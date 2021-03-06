package model;


import javax.persistence.*;
import java.io.Serializable;


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

    private float rating;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "type")
    private String type;


    public Exercise() {
    }

    public int getId() {
        return this.id;
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

    public float getRating() {
        return this.rating;
    }

    public void setRating(float rating) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exercise exercise = (Exercise) o;

        return (musclegroup != null ? musclegroup.equals(exercise.musclegroup) : exercise.musclegroup == null) && (name != null ? name.equals(exercise.name) : exercise.name == null);
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