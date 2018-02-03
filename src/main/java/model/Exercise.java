package model;


import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the exercise database table.
 */
@Entity
@NamedQuery(name = "Exercise.findAll", query = "SELECT e FROM Exercise e")
public class Exercise implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String musclegroup;

    private String name;

    @Column(name = "pics_url")
    private String picsUrl;

    @Column(name = "description")
    private String description;

    private String rating;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "type")
    private String type;

    //bi-directional many-to-one association to Lift
    @OneToMany(mappedBy = "exercise")
    private List<Lift> lifts;



    public Exercise() {
    }
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMusclegroup() {
        return this.musclegroup;
    }

    public void setMusclegroup(String musclegroup) {
        this.musclegroup = musclegroup;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPicsUrl() {
        return this.picsUrl;
    }

    public void setPicsUrl(String picsUrl) {
        this.picsUrl = picsUrl;
    }

    public String getRating() {
        return this.rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public List<Lift> getLifts() {
        return this.lifts;
    }

    public void setLifts(List<Lift> lifts) {
        this.lifts = lifts;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Lift addLift(Lift lift) {
        getLifts().add(lift);
        lift.setExercise(this);

        return lift;
    }

    public Lift removeLift(Lift lift) {
        getLifts().remove(lift);
        lift.setExercise(null);

        return lift;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        if (musclegroup != null ? !musclegroup.equals(exercise.musclegroup) : exercise.musclegroup != null)
            return false;
        return name != null ? name.equals(exercise.name) : exercise.name == null;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "musclegroup='" + musclegroup + '\'' +
                ", name='" + name + '\'' +
                ", picsUrl='" + picsUrl + '\'' +
                ", description='" + description + '\'' +
                ", rating='" + rating + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

}