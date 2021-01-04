package com.gym.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "MealLog")
@Table(name = "meal_log")
public class MealLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_log_id")
    private int id;

    @Column(name = "meal_log_date")
    private LocalDate submitDate;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "meal_log_user")
    private User user;

    @Column(name = "meal_log_portionCount")
    private float portionCount;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "meal_log_reference")
    private Meal referredMeal;

    public MealLog() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(LocalDate submitDate) {
        this.submitDate = submitDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getPortionCount() {
        return portionCount;
    }

    public void setPortionCount(float portionCount) {
        this.portionCount = portionCount;
    }

    public Meal getReferredMeal() {
        return referredMeal;
    }

    public void setReferredMeal(Meal referredMeal) {
        this.referredMeal = referredMeal;
    }
}
