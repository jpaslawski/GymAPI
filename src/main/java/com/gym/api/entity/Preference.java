package com.gym.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "Preference")
@Table(name="user_preferences")
public class Preference {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "preference_id")
    private int id;

    @Column(name = "preference_language")
    private String preferredLanguage;

    @Column(name = "preference_layout")
    private String preferredLayout;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "preference_user", referencedColumnName = "user_id")
    private User user;

    public Preference() {
    }

    public Preference(String preferredLanguage, String preferredLayout, User user) {
        this.preferredLanguage = preferredLanguage;
        this.preferredLayout = preferredLayout;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPreferredLanguage() {
        return preferredLanguage;
    }

    public void setPreferredLanguage(String preferredLanguage) {
        this.preferredLanguage = preferredLanguage;
    }

    public String getPreferredLayout() {
        return preferredLayout;
    }

    public void setPreferredLayout(String preferredLayout) {
        this.preferredLayout = preferredLayout;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
