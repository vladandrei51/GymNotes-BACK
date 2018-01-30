package model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the lift database table.
 */
@Entity
@NamedQuery(name = "Lift.findAll", query = "SELECT l FROM Lift l")
public class Lift implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @JsonIgnore
    private int id;

    private String notes;

    private int reps;

    @Column(name = "set_date")
    private Timestamp setDate;

    private int weight;

    //bi-directional many-to-one association to Exercise
    @ManyToOne
    @JsonIgnore
    private Exercise exercise;

    public Lift() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getReps() {
        return this.reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public Timestamp getSetDate() {
        return this.setDate;
    }

    public void setSetDate(Timestamp setDate) {
        this.setDate = setDate;
    }

    public int getWeight() {
        return this.weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public Exercise getExercise() {
        return this.exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

}