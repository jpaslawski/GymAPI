package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Exercise")
@Table(name = "exercise")
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_id")
    private int id;

    @Column(name = "exercise_name")
    private String name;

    @Column(name = "exercise_info")
    private String info;

    @Column(name = "exercise_isPublic")
    private boolean isPublic;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "category")
    private ExerciseCategory exerciseCategory;

    @JsonIgnore
    @ManyToMany(mappedBy = "exercises")
    private Set<Workout> workouts = new HashSet<>();

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "author")
    private User author;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "referredExercise",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ExerciseLog> exerciseLogs;

    public Exercise() {

    }

    public Exercise(String name, String info, boolean isPublic) {
        this.name = name;
        this.info = info;
        this.isPublic = isPublic;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
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

    public Set<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(Set<Workout> workouts) {
        this.workouts = workouts;
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
