package utils;

import model.Exercise;
import model.MuscleGroup;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class HTMLParser {
    private Document doc;
    private static final String BASE_URL = "https://www.bodybuilding.com/";
    public static final String EXERCISES_URL = "https://www.bodybuilding.com/exercises";
    private static final String NO_EXERCISES = "No exercises were found that matched the selected options.";

    public HTMLParser(String url) throws IOException {
        doc = Jsoup.connect(url).get();
    }

    public HashSet<Exercise> getExercises() throws IOException {
        HashSet<Exercise> exercises = new HashSet<>();
        HashMap<String, MuscleGroup> links = allExerciseLinks();
        for (HashMap.Entry<String, MuscleGroup> entry : links.entrySet()) {
            this.doc = Jsoup.connect(entry.getKey()).get();
            Exercise exercise = new Exercise();
            if (getExerciseName().length() == 0 || getExercisePics().length() == 0 && getExerciseType().length() == 0)
                continue;
            exercise.setName(getExerciseName());
            exercise.setVideoUrl(getExerciseVideo().length() > 0 ? getExerciseVideo() : "");
            exercise.setMusclegroup(entry.getValue().getName());
            exercise.setRating((getExerciseRating().equals("n/a") ? 0 : getExerciseRating().length() > 0 ? Float.valueOf(getExerciseRating()) : 0));
            exercise.setPicsUrl(getExercisePics().length() > 0 ? getExercisePics() : "");
            exercise.setDescription(getExerciseDescription().length() > 0 ? getExerciseDescription() : "");
            exercise.setType(getExerciseType().length() > 0 ? getExerciseType() : "");


            exercises.add(exercise);
        }
        return exercises;
    }

    private String getExerciseName() {
        return doc.getElementsByClass("ExDetail-h2").text();
    }

    private String getExerciseVideo() {
        return doc.getElementById("js-ex-jwplayer-video").attr("data-src");
    }

    private String getExerciseRating() {
        Elements elements = doc.getElementsByClass("grid-3");
        String rating = "";
        for (Element element : elements) {
            if (element.children().hasText())
                rating = element.children().text();
        }
        return rating.replaceAll(" .*", "");
    }

    private String getExerciseDescription() {
        return doc.select("ol[class=ExDetail-descriptionSteps]").text();
    }

    private String getExerciseType() {
        String type;
        Elements div = doc.getElementsByClass("bb-list--plain");
        type = div.select("a").first().text();
        return type;
    }

    private String getExercisePics() {
        List<String> images = new ArrayList<>();
        StringBuilder returnString = new StringBuilder();
        Elements elements = doc.getElementsByClass("js-ex-enlarge");
        for (Element element : elements) {
            images.add(element.attr("src"));
        }
        for (String image : images) {
            returnString.append(image);
            returnString.append(" ");
        }
        return returnString.substring(0, returnString.length() - 1);
    }


    private HashMap<String, MuscleGroup> allExerciseLinks() throws IOException {
        HashMap<String, MuscleGroup> links = new HashMap<>();

        for (MuscleGroup muscleGroup : MuscleGroup.values()) {
            for (String link : exerciseLinksFromMuscleGroup(muscleGroup.getUrl()))
                links.put(link, muscleGroup);
        }
        return links;
    }


    private HashSet<String> getSubLinksFromMG(String url) throws IOException {
        String paramURL = url;
        HashSet<String> links = new HashSet<>();

        this.doc = Jsoup.connect(url).get();
        for (int i = 1; !doc.getElementsByClass("ExCategory-results").select("p").first().text().equals(NO_EXERCISES); i++) {
            url = paramURL;
            url += "/" + i;
            this.doc = Jsoup.connect(url).get();
            if (!doc.getElementsByClass("ExCategory-results").select("p").first().text().equals(NO_EXERCISES)) {
                links.add(url);
            }
        }
        return links;
    }

    private HashSet<String> exerciseLinksFromMuscleGroup(String url) throws IOException {
        HashSet<String> allLinks = new HashSet<>();
        HashSet<String> allSubLinks = getSubLinksFromMG(url);
        for (String link : allSubLinks) {
            this.doc = Jsoup.connect(link).get();
            Elements elements = doc.getElementsByClass("ExResult-resultsHeading");
            for (Element element : elements) {
                if (element.children().hasAttr("href"))
                    allLinks.add(BASE_URL + element.children().attr("href"));
            }
        }
        return allLinks;
    }
}
