package model;

import java.util.HashMap;
import java.util.Map;

public enum MuscleGroup {
    NECK("neck", "Neck"),
    TRAPS("traps", "Trapezius"),
    SHOULDERS("shoulders", "Shoulders"),
    CHEST("chest", "Chest"),
    BICEPS("biceps", "Biceps"),
    FOREARMS("forearms", "Forearms"),
    ABS("abdominals", "Abs"),
    QUADS("quadriceps", "Quads"),
    CALVES("calves", "Calves"),
    TRICEPS("triceps", "Triceps"),
    LATS("lats", "Lats"),
    MIDDLE_BACK("middle-back", "Middle Back"),
    LOWER_BACK("lower-back", "Lower Back"),
    GLUTES("glutes", "Glutes"),
    HAMSTRINGS("hamstrings", "Hamstrings");

    String url;
    String name;
    private static final String EXERCISES_URL = "https://www.bodybuilding.com/exercises/muscle/";


    private static Map<String, MuscleGroup> nameEnumMap = new HashMap<String, MuscleGroup>();
    private static Map<String, MuscleGroup> URLEnumMap = new HashMap<String, MuscleGroup>();


    static {
        for (MuscleGroup muscleGroup : MuscleGroup.values()) {
            nameEnumMap.put(muscleGroup.name, muscleGroup);
        }

        for (MuscleGroup muscleGroup1 : MuscleGroup.values()) {
            URLEnumMap.put(muscleGroup1.url, muscleGroup1);
        }
    }


    public static MuscleGroup getEnumFromName(String name) {
        return nameEnumMap.get(name);
    }

    public static MuscleGroup getEnumFromURL(String URL) {
        return URLEnumMap.get(EXERCISES_URL + URL);
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    MuscleGroup(String url, String name) {
        this.url = EXERCISES_URL + url;
        this.name = name;
    }

}
