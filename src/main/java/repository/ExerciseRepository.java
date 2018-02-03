package repository;

import model.Exercise;
import model.Lift;
import model.MuscleGroup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ExerciseRepository implements IRepository<Exercise> {

    private EntityManager entitymanager;
    private EntityManagerFactory emfactory;

    public ExerciseRepository() {
        emfactory = Persistence.createEntityManagerFactory("Eclipselink_JPA");
        entitymanager = emfactory.createEntityManager();
    }


    @Override
    public Exercise getOne(Exercise exercise) {
        return entitymanager.find(Exercise.class, exercise.getId());
    }

    public Exercise findOneByID(int id) {
        return entitymanager.find(Exercise.class, id);
    }

    @Override
    public List<Exercise> getAll() {
        TypedQuery<Exercise> query = entitymanager.createNamedQuery("Exercise.findAll", Exercise.class);
        return query.getResultList();
    }


    @Override
    public int size() {
        return getAll().size();
    }

    public int getIDFromName(String name){
        for (Exercise exercise : getAll()){
            if (exercise.getName().equals(name)) return exercise.getId();
        }
        return -1;
    }

    @Override
    public Exercise add(Exercise obj) {

        if (getAll().contains(obj))
            return null;

        entitymanager.getTransaction().begin();

        Exercise exercise = new Exercise();

        exercise.setLifts(null);
        exercise.setName(obj.getName());
        exercise.setPicsUrl(obj.getPicsUrl());
        exercise.setRating(obj.getRating());
        exercise.setVideoUrl(obj.getVideoUrl());
        exercise.setMusclegroup(obj.getMusclegroup());
        exercise.setDescription(obj.getDescription());
        exercise.setType(obj.getType());

        //        int initialSize = size();

        entitymanager.persist(exercise);

        entitymanager.getTransaction().commit();

        //        if (initialSize != size()){
        //            Exercise latestExercise = getLatest();
        //
        //            if (obj.getLifts().size() > 0){
        //                List<Lift> lifts = obj.getLifts();
        //
        //                Lift newLift = new Lift();
        //                for (Lift lift : lifts){
        //                    newLift.setExercise(latestExercise);
        //                    newLift.setNotes(lift.getNotes());
        //                    newLift.setReps(lift.getReps());
        //                    newLift.setSetDate(lift.getSetDate());
        //                    newLift.setWeight(lift.getWeight());
        //                    EntityManager em = emfactory.createEntityManager();
        //                    em.getTransaction().begin();
        //                    em.persist(newLift);
        //                    em.getTransaction().commit();
        //                    em.close();
        //
        //                }
        //            }
        //            return exercise;
        //        }
        //
        //        return null;
        return exercise;
    }

    @Override
    public void delete(int id) {
        entitymanager.getTransaction().begin();
        Exercise exercise = findOneByID(id);
        List<Lift> lifts = exercise.getLifts();

        for (Lift lift : lifts) {
            entitymanager.remove(lift);
        }

        exercise.setDescription(null);
        exercise.setPicsUrl(null);
        exercise.setRating(null);
        exercise.setMusclegroup(null);
        exercise.setVideoUrl(null);
        exercise.setName(null);
        exercise.setLifts(null);
        exercise.setType(null);
        entitymanager.remove(exercise);
        entitymanager.getTransaction().commit();
    }


    public List<Exercise> getByMuscleGroupURL(MuscleGroup muscleGroup) {
        List<Exercise> exercises = new ArrayList<>();
        for (Exercise exercise : getAll()) {
            MuscleGroup muscleGroup1 = MuscleGroup.getEnumFromName(exercise.getMusclegroup());
            if (muscleGroup1.getUrl().equals(muscleGroup.getUrl())) {
                exercises.add(exercise);
            }
        }
        return exercises;
    }

}