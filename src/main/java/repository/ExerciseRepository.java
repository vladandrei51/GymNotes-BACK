package repository;

import model.Exercise;
import model.MuscleGroup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

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

    public int getIDFromName(String name) {
        for (Exercise exercise : getAll()) {
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

        exercise.setName(obj.getName());
        exercise.setPicsUrl(obj.getPicsUrl());
        exercise.setRating(obj.getRating());
        exercise.setVideoUrl(obj.getVideoUrl());
        exercise.setMusclegroup(obj.getMusclegroup());
        exercise.setDescription(obj.getDescription());
        exercise.setType(obj.getType());


        entitymanager.persist(exercise);

        entitymanager.getTransaction().commit();

        return exercise;
    }

    @Override
    public void delete(int id) {
        entitymanager.getTransaction().begin();
        Exercise exercise = findOneByID(id);


        exercise.setDescription(null);
        exercise.setPicsUrl(null);
        exercise.setRating(null);
        exercise.setMusclegroup(null);
        exercise.setVideoUrl(null);
        exercise.setName(null);
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