import model.Exercise;
import repository.ExerciseRepository;
import utils.HTMLParser;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {

        addExercisesFromParser();
//        ExerciseRepository exerciseRepository = new ExerciseRepository();
//        System.out.println(exerciseRepository.getByMuscleGroupURL(MuscleGroup.GLUTES));
    }

    private static void addExercisesFromParser() {
        try {
            HTMLParser muscleGroupsFinder = new HTMLParser(HTMLParser.EXERCISES_URL);
            ExerciseRepository repository = new ExerciseRepository();
            for (Exercise exercise : muscleGroupsFinder.getExercises()) {
                repository.add(exercise);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
