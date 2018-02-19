package repository;

import model.Exercise;
import model.MuscleGroup;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import java.util.HashSet;
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
    public HashSet<Exercise> getAll() {
        TypedQuery<Exercise> query = entitymanager.createNamedQuery("Exercise.findAll", Exercise.class);
        return new HashSet<>(query.getResultList());
    }


    @Override
    public int size() {
        return getAll().size();
    }


    @Override
    public Exercise add(Exercise obj) {

        entitymanager.getTransaction().begin();


        entitymanager.persist(obj);

        entitymanager.getTransaction().commit();

        return obj;
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


    public HashSet<Exercise> getByMuscleGroupURL(MuscleGroup muscleGroup) {
        return getAll().stream().filter(e -> e.getMusclegroup().equals(muscleGroup.getUrl())).collect(Collectors.toCollection(HashSet::new));
    }

}