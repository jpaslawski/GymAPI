package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "ExerciseCategory")
@Table(name = "exercise_category")
public class ExerciseCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ec_id")
    private int id;

    @Column(name = "ec_category")
    private String category;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "exerciseCategory",
            cascade = {CascadeType.PERSIST,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private Set<Exercise> connectedExercises;

    public ExerciseCategory() {
    }

    public ExerciseCategory(String category) {
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Set<Exercise> getConnectedExercises() {
        return connectedExercises;
    }

    public void setConnectedExercises(Set<Exercise> connectedExercises) {
        this.connectedExercises = connectedExercises;
    }
}
