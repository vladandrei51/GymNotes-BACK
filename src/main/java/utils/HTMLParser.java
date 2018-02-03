package utils;

import model.Exercise;
import model.MuscleGroup;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class HTMLParser {
    private Document doc;
    private static final String BASE_URL = "https://www.bodybuilding.com/";
    public static final String EXERCISES_URL = "https://www.bodybuilding.com/exercises";
    private static final String NO_EXERCISES = "No exercises were found that matched the selected options.";

    public HTMLParser(String url) throws IOException {
        doc = Jsoup.connect(url).get();
    }

    public List<Exercise> getExercises() throws IOException {
        ArrayList<Exercise> exercises = new ArrayList<>();
        Map<String, MuscleGroup> links = allExerciseLinks();
        for (Map.Entry<String, MuscleGroup> entry : links.entrySet()) {
            this.doc = Jsoup.connect(entry.getKey()).get();
            Exercise exercise = new Exercise();
            exercise.setName(getExerciseName());
            exercise.setVideoUrl(getExerciseVideo());
            exercise.setMusclegroup(entry.getValue().getName());
            exercise.setRating(getExerciseRating());
            exercise.setPicsUrl(getExercisePics());
            exercise.setDescription(getExerciseDescription());
            exercise.setType(getExerciseType());

            if (exercise.getName().length() > 0)
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

    private String getExerciseDescription() throws IOException {
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


    private Map<String, MuscleGroup> allExerciseLinks() throws IOException {
        Map<String, MuscleGroup> links = new HashMap<>();

        for (MuscleGroup muscleGroup : MuscleGroup.values()) {
            for (String link : exerciseLinksFromMuscleGroup(muscleGroup.getUrl()))
                links.put(link, muscleGroup);
        }
        return links;
    }


    private ArrayList<String> getSubLinksFromMG(String url) throws IOException {
        String paramURL = url;
        ArrayList<String> links = new ArrayList<>();

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

    private ArrayList<String> exerciseLinksFromMuscleGroup(String url) throws IOException {
        ArrayList<String> allLinks = new ArrayList<>();
        ArrayList<String> allSubLinks = getSubLinksFromMG(url);
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
