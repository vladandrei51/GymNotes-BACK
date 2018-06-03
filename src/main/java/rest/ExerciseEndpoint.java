package rest;


import model.Exercise;
import model.MuscleGroup;
import repository.ExerciseRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

// /api/exercises
@Path("/exercises")
public class ExerciseEndpoint {
    @Inject
    private ExerciseRepository repository;

    @Path("/all")
    @GET
    @Produces(APPLICATION_JSON)
    /*
       Returns all the exercises in the db
    */
    public Response getAll() {
        List<Exercise> exercises = repository.getAll();
        if (exercises.size() == 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(exercises).build();
    }


    @GET
    @Path("/get/musclegroup/{musclegroup}")
    @Produces(APPLICATION_JSON)
    /*
        Returns all the exercise from a specifc muscle group
    */
    public Response getByMuscleGroup(@PathParam("musclegroup") String url) {
        List<Exercise> exercises = repository.getByMuscleGroupURL(MuscleGroup.getEnumFromURL(url));
        if (exercises.size() == 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(exercises).build();
    }

    @GET
    @Path("/get/cardio")
    @Produces(APPLICATION_JSON)
    /*
        Returns all the exercise from a specifc muscle group
    */
    public Response getByMuscleGroup() {
        List<Exercise> exercises = repository.getAllCardio();
        if (exercises.size() == 0) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(exercises).build();
    }

    @GET
    @Path("/get/id/{id}")
    @Produces(APPLICATION_JSON)
    /*
        Gets exercise by ID
    */
    public Response getExerciseByID(@PathParam("id") int id) {
        Exercise exercise = repository.findOneByID(id);
        if (exercise == null) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        return Response.ok(exercise).build();
    }


    @GET
    @Path("/size")
    @Produces(TEXT_PLAIN)
    public Response size() {
        int count = repository.size();

        if (count == 0)
            return Response.status(Response.Status.NO_CONTENT).build();

        return Response.ok(count).build();
    }


    @POST
    @Path("/add")
    @Consumes(MediaType.APPLICATION_JSON)
    public Exercise AddExercise(Exercise exercise) {
        return repository.add(exercise);
    }


}