package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

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
    @JoinColumn(name = "connected_workout")
    @JsonIgnore
    private Workout connectedWorkout;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "author")
    @JsonIgnore
    private User author;

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

    public Workout getConnectedWorkout() {
        return connectedWorkout;
    }

    public void setConnectedWorkout(Workout connectedWorkout) {
        this.connectedWorkout = connectedWorkout;
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
        if (!(o instanceof Exercise )) return false;
        return false;
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
