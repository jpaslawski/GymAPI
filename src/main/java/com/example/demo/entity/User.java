package com.example.demo.entity;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_name")
    private String username;

    @Column(name ="user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_weight")
    private Float weight;

    @Column(name = "user_height")
    private Float height;

    @Column(name = "user_date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "user_gender")
    private String gender;

    @Column(name = "user_exercise_level")
    private Float exerciseLevel;

    @Column(name = "user_permissions")
    private String permissions;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "author",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Workout> workouts;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "author",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Exercise> exercises;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "trainee",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<ExerciseLog> exerciseLogs;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Meal> meals;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<WeightLog> weightLogs;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<MealLog> mealLogs;

    @OneToOne(mappedBy = "user")
    private UserDiet userDiet;

    public User() {
    }

    public User(String username, String email, String password, Float weight, Float height, LocalDate dateOfBirth, String gender, Float exerciseLevel, String permissions) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.exerciseLevel = exerciseLevel;
        this.permissions = permissions;
    }

    /** Getters and setters **/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Float getExerciseLevel() {
        return exerciseLevel;
    }

    public void setExerciseLevel(Float exerciseLevel) {
        this.exerciseLevel = exerciseLevel;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    /** User reference methods **/

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public List<ExerciseLog> getExerciseLogs() {
        return exerciseLogs;
    }

    public void setExerciseLogs(List<ExerciseLog> exerciseLogs) {
        this.exerciseLogs = exerciseLogs;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

    public List<WeightLog> getWeightLogs() {
        return weightLogs;
    }

    public void setWeightLogs(List<WeightLog> weightLogs) {
        this.weightLogs = weightLogs;
    }

    public List<MealLog> getMealLogs() {
        return mealLogs;
    }

    public void setMealLogs(List<MealLog> mealLogs) {
        this.mealLogs = mealLogs;
    }

    public UserDiet getUserDiet() {
        return userDiet;
    }

    public void setUserDiet(UserDiet userDiet) {
        this.userDiet = userDiet;
    }

    public boolean isValid() {
        return username != null && email != null && password != null && weight != null && height != null && dateOfBirth != null && permissions != null;
    }

    /** Add or remove User reference to other classes **/

    public void addWorkout(Workout workout) {
        if (workouts == null) {
            workouts = new ArrayList<>();
        }
        workouts.add(workout);
        workout.setAuthor(this);
    }

    public void removeWorkout(Workout workout) {
        if (workouts == null) {
            workouts = new ArrayList<>();
        }
        workouts.remove(workout);
        workout.setAuthor(null);
    }

    public void addExercise(Exercise exercise) {
        if (exercises == null) {
            exercises = new ArrayList<>();
        }
        exercises.add(exercise);
        exercise.setAuthor(this);
    }

    public void removeExercise(Exercise exercise) {
        if (exercises == null) {
            exercises = new ArrayList<>();
        }
        exercises.remove(exercise);
        exercise.setAuthor(null);
    }

    public void addExerciseLog(ExerciseLog exerciseLog) {
        if (exerciseLogs == null) {
            exerciseLogs = new ArrayList<>();
        }
        exerciseLogs.add(exerciseLog);
        exerciseLog.setTrainee(this);
    }

    public void removeExerciseLog(ExerciseLog exerciseLog) {
        if (exerciseLogs == null) {
            exerciseLogs = new ArrayList<>();
        }
        exerciseLogs.remove(exerciseLog);
        exerciseLog.setTrainee(null);
    }

    public void addMeal(Meal meal) {
        if (meals == null) {
            meals = new ArrayList<>();
        }
        meals.add(meal);
        meal.setUser(this);
    }

    public void removeMeal(Meal meal) {
        if (meals == null) {
            meals = new ArrayList<>();
        }
        meals.remove(meal);
        meal.setUser(null);
    }

    public void addWeightLog(WeightLog weightLog) {
        if (weightLogs == null) {
            weightLogs = new ArrayList<>();
        }
        weightLogs.add(weightLog);
        weightLog.setUser(this);
    }

    public void removeWeightLog(WeightLog weightLog) {
        if (weightLogs == null) {
            weightLogs = new ArrayList<>();
        }
        weightLogs.remove(weightLog);
        weightLog.setUser(null);
    }

    public void addMealLog(MealLog mealLog) {
        if (mealLogs == null) {
            mealLogs = new ArrayList<>();
        }
        mealLogs.add(mealLog);
        mealLog.setUser(this);
    }

    public void removeWeightLog(MealLog mealLog) {
        if (mealLogs == null) {
            mealLogs = new ArrayList<>();
        }
        mealLogs.remove(mealLog);
        mealLog.setUser(null);
    }
}