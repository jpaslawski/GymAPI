package com.gym.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Exercise")
@Table(name = "exercise")
@Indexed
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private int id;

    @Column(name = "exercise_name")
    private String name;

    @Column(name = "exercise_name_pl")
    private String namePL;

    @Column(name = "exercise_info")
    private String info;

    @Column(name = "exercise_info_pl")
    private String infoPL;

    @Column(name = "exercise_status")
    private Status status;

    @ManyToOne(cascade = {CascadeType.PERSIST,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "category")
    @Fetch(FetchMode.SELECT)
    private ExerciseCategory exerciseCategory;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "exercises")
    @Fetch(FetchMode.SUBSELECT)
    private Set<Workout> workouts = new HashSet<>();

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "author")
    @Fetch(FetchMode.SELECT)
    private User author;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "referredExercise",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @Fetch(FetchMode.SELECT)
    private List<ExerciseLog> exerciseLogs;

    public Exercise() {

    }

    public Exercise(String name, String namePL, String info, String infoPL, Status status) {
        this.name = name;
        this.namePL = namePL;
        this.info = info;
        this.infoPL = infoPL;
        this.status = status;
    }

    public Exercise(String name, String info, Status status, ExerciseCategory exerciseCategory, Set<Workout> workouts, User author, List<ExerciseLog> exerciseLogs) {
        this.name = name;
        this.info = info;
        this.status = status;
        this.exerciseCategory = exerciseCategory;
        this.workouts = workouts;
        this.author = author;
        this.exerciseLogs = exerciseLogs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamePL() {
        return namePL;
    }

    public void setNamePL(String namePL) {
        this.namePL = namePL;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfoPL() {
        return infoPL;
    }

    public void setInfoPL(String infoPL) {
        this.infoPL = infoPL;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public ExerciseCategory getExerciseCategory() {
        return exerciseCategory;
    }

    public void setExerciseCategory(ExerciseCategory category) {
        this.exerciseCategory = category;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(Set<Workout> workouts) {
        this.workouts = workouts;
    }

    public List<ExerciseLog> getExerciseLogs() {
        return exerciseLogs;
    }

    public void setExerciseLogs(List<ExerciseLog> exerciseLogs) {
        this.exerciseLogs = exerciseLogs;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", info='" + info + '\'' +
                ", exerciseCategory=" + exerciseCategory +
                ", workouts=" + workouts +
                ", author=" + author +
                ", exerciseLogs=" + exerciseLogs +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Exercise)) return false;
        return false;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    public void addExerciseLog(ExerciseLog exerciseLog) {
        if (exerciseLogs == null) {
            exerciseLogs = new ArrayList<>();
        }

        exerciseLogs.add(exerciseLog);
        exerciseLog.setReferredExercise(this);
    }

    public void removeExerciseLog(ExerciseLog exerciseLog) {
        if (exerciseLogs == null) {
            exerciseLogs = new ArrayList<>();
        }

        exerciseLogs.remove(exerciseLog);
        exerciseLog.setReferredExercise(null);
    }
}
