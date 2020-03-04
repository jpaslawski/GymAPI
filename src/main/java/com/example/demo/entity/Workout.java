package com.example.demo.entity;


import javax.persistence.*;

@Entity
@Table(name="workout")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private int id;

    @Column(name = "workout_name")
    private String name;

    @Column(name = "workout_info")
    private String info;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "workout_author")
    private User author;

    @Column(name = "workout_isPublic")
    private boolean isPublic;

    public Workout() {

    }

    public Workout(String name, String info, User author, boolean isPublic) {
        this.name = name;
        this.info = info;
        this.author = author;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }
}
