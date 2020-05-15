package com.example.demo.dao;

import com.example.demo.entity.Exercise;
import com.example.demo.entity.ExerciseCategory;
import com.example.demo.entity.User;
import com.example.demo.entity.Workout;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class GymDAOImpl implements GymDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public String getEmailFromToken(String header) {

        // Parse token trough signing key
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey("iFuZc|_6D{UBn(A".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(header.replace("Bearer ", ""));

        return claimsJws.getBody().get("email").toString();
    }

    /** Get a list of all the users **/
    @Override
    public List<User> getUsers() {

        Session currentSession = sessionFactory.getCurrentSession();

        Query<User> theQuery =
                currentSession.createQuery("FROM User ORDER BY id", User.class);

        return theQuery.getResultList();
    }

    /** Save or update user account information - Sign Up **/
    @Override
    public void saveUser(User user) {

        Session currentSession = sessionFactory.getCurrentSession();

        currentSession.saveOrUpdate(user);

    }

    /** Get user account information **/
    @Override
    public User getUser(int id) {

        Session currentSession = sessionFactory.getCurrentSession();

        User user = currentSession.get(User.class, id);

        return user;
    }

    /** Get user account information by providing his email **/
    @Override
    public User getUserByEmail(String userEmail) {

        Session currentSession = sessionFactory.getCurrentSession();

        Query query =
                currentSession.createQuery("FROM User WHERE email= :userEmail");
        query.setParameter("userEmail", userEmail);

        List list = query.list();

        if (list.isEmpty()) {
            return null;
        }

        return (User) list.get(0);
    }

    /** Delete user account **/
    @Override
    public void deleteUser(int userId) {

        Session currentSession = sessionFactory.getCurrentSession();

        Query theQuery =
                currentSession.createQuery("DELETE FROM User WHERE id=:userId");
        theQuery.setParameter("userId", userId);

        theQuery.executeUpdate();
    }

    /** Get a list of all the workouts **/
    @Override
    public List<Workout> getWorkouts() {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Workout> theQuery =
                currentSession.createQuery("FROM Workout", Workout.class);

        return theQuery.getResultList();
    }

    /** Get a list of all workouts of the given user **/
    @Override
    public List<Workout> getWorkoutsByUserId(int userId) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Workout> theQuery =
                currentSession.createQuery("FROM Workout WHERE author=:userId", Workout.class);
        theQuery.setParameter("userId", userId);
        return theQuery.getResultList();
    }


    /** Save or update a workout **/
    @Override
    public void saveWorkout(String email, Workout workout) {

        Session currentSession = sessionFactory.getCurrentSession();

        User user = getUserByEmail(email);
        workout.setAuthor(user);
        user.addWorkout(workout);

        currentSession.saveOrUpdate(workout);
    }

    /** Get a workout using its ID **/
    @Override
    public Workout getWorkout(int workoutId) {

        Workout workout = sessionFactory.getCurrentSession().find(Workout.class, workoutId);
        return workout;
    }

    /** Delete a workout by ID **/
    @Override
    public void deleteWorkout(int workoutId) {

        Session currentSession = sessionFactory.getCurrentSession();
        Workout workout = currentSession.find(Workout.class, workoutId);

        for (Exercise exercise : workout.getExercises()) {
            workout.removeExercise(exercise);
        }

        currentSession.remove(workout);
    }

    /** Get a list of all exercises **/
    @Override
    public List<Exercise> getExercises() {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Exercise> theQuery =
                currentSession.createQuery("FROM Exercise", Exercise.class);

        return theQuery.getResultList();
    }

    /** Get a list of all exercises connected with the given workout **/
    @Override
    public Set<Exercise> getExercisesByWorkoutId(int workoutId) {
        Session currentSession = sessionFactory.getCurrentSession();

        Workout workout = currentSession.find(Workout.class, workoutId);

        return workout.getExercises();
    }

    /** Get a list of all exercises of the given category **/
    @Override
    public Set<Exercise> getExercisesByCategory(String category) {
        Session currentSession = sessionFactory.getCurrentSession();
        ExerciseCategory exerciseCategory = getCategoryByName(category);
        if (exerciseCategory == null) {
            return null;
        }

        Query<Exercise> theQuery =
                currentSession.createQuery("FROM Exercise WHERE exerciseCategory=:category", Exercise.class);
        theQuery.setParameter("category", exerciseCategory);

        return new HashSet<>(theQuery.getResultList());
    }

    /** Save or update an exercise **/
    @Override
    public void saveExercise(String email, Exercise exercise, String category) {
        Session currentSession = sessionFactory.getCurrentSession();

        User user = getUserByEmail(email);

        Query<ExerciseCategory> theQuery =
                currentSession.createQuery("FROM ExerciseCategory WHERE category=:category", ExerciseCategory.class);
        theQuery.setParameter("category", category);

        ExerciseCategory exerciseCategory = theQuery.getSingleResult();
        exercise.setExerciseCategory(exerciseCategory);

        user.addExercise(exercise);

        currentSession.saveOrUpdate(exercise);
    }

    /** Add existing exercise to workout (user is already set) **/
    @Override
    public void addExerciseToWorkout(int exerciseId, int workoutId) {
        Session currentSession = sessionFactory.getCurrentSession();

        Workout workout = getWorkout(workoutId);
        Exercise exercise = getExercise(exerciseId);
        workout.addExercise(exercise);
        workout.setExerciseAmount(workout.getExerciseAmount() + 1);

        currentSession.saveOrUpdate(exercise);
    }

    /** Create a new exercise and add it to a workout as one operation **/
    @Override
    public void addNewExerciseToWorkout(String email, int workoutId, Exercise exercise, String category) {
        Session currentSession = sessionFactory.getCurrentSession();

        User user = getUserByEmail(email);
        Workout workout = getWorkout(workoutId);

        Query<ExerciseCategory> theQuery =
                currentSession.createQuery("FROM ExerciseCategory WHERE category=:category", ExerciseCategory.class);
        theQuery.setParameter("category", category);

        ExerciseCategory exerciseCategory = theQuery.getSingleResult();
        exercise.setExerciseCategory(exerciseCategory);

        user.addExercise(exercise);
        workout.addExercise(exercise);

        currentSession.saveOrUpdate(exercise);

        workout.setExerciseAmount(workout.getExerciseAmount() + 1);
        currentSession.saveOrUpdate(workout);
    }

    /** Get an exercise using its ID **/
    @Override
    public Exercise getExercise(int exerciseId) {
        Exercise exercise = sessionFactory.getCurrentSession().find(Exercise.class, exerciseId);
        return exercise;
    }

    /** Delete an exercise by ID **/
    @Override
    public void deleteExercise(int exerciseId) {

        Session currentSession = sessionFactory.getCurrentSession();

        Query theQuery =
                currentSession.createQuery("DELETE FROM Exercise WHERE id=:exerciseId");
        theQuery.setParameter("exerciseId", exerciseId);

        theQuery.executeUpdate();
    }

    /** Delete an exercise from the given workout **/
    @Override
    public void deleteExerciseFromWorkout(int exerciseId, int workoutId) {
        Session currentSession = sessionFactory.getCurrentSession();
        Workout workout = currentSession.find(Workout.class, workoutId);
        Exercise exercise = currentSession.find(Exercise.class, exerciseId);

        workout.removeExercise(exercise);

        workout.setExerciseAmount(workout.getExerciseAmount() - 1);
    }

    /** Get the category of the given name **/
    @Override
    public ExerciseCategory getCategoryByName(String category) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<ExerciseCategory> theQuery =
                currentSession.createQuery("FROM ExerciseCategory WHERE category LIKE :category", ExerciseCategory.class);
        theQuery.setParameter("category", "%" + category + "%");

        return theQuery.getSingleResult();
    }

    /** Get a list of all exercise categories **/
    @Override
    public List<ExerciseCategory> getCategories() {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<ExerciseCategory> theQuery =
                currentSession.createQuery("FROM ExerciseCategory", ExerciseCategory.class);

        return theQuery.getResultList();
    }

    /** Add exercise category **/
    @Override
    public void addExerciseCategory(ExerciseCategory exerciseCategory) {
        Session currentSession = sessionFactory.getCurrentSession();
        currentSession.saveOrUpdate(exerciseCategory);
    }

}
