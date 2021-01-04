package com.gym.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "WeightLog")
@Table(name = "weight_log")
public class WeightLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weight_log_id")
    private int id;

    @Column(name = "weight_log_current_weight")
    private float currentWeight;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "weight_log_trainee")
    private User user;

    @Column(name = "weight_log_date")
    private LocalDate submitDate;

    public WeightLog() {
    }

    public WeightLog(float currentWeight, User user, LocalDate submitDate) {
        this.currentWeight = currentWeight;
        this.user = user;
        this.submitDate = submitDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(float currentWeight) {
        this.currentWeight = currentWeight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(LocalDate submitDate) {
        this.submitDate = submitDate;
    }
}
