package com.example.demo.dao;

import com.example.demo.entity.Exercise;
import com.example.demo.entity.User;
import com.example.demo.entity.Workout;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GymDAOImpl implements GymDAO {

    @Autowired
    private SessionFactory sessionFactory;

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

        User user = (User) list.get(0);

        return user;
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
    public void saveWorkout(int userId, Workout workout) {

        Session currentSession = sessionFactory.getCurrentSession();

        User user = getUser(userId);

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

        Query theQuery =
                currentSession.createQuery("DELETE FROM Workout WHERE id=:workoutId");
        theQuery.setParameter("workoutId", workoutId);

        theQuery.executeUpdate();
    }



    /** Get a list of all exercises **/
    @Override
    public List<Exercise> getExercises() {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Exercise> theQuery =
                currentSession.createQuery("FROM Exercise", Exercise.class);

        return theQuery.getResultList();
    }

    /** Save or update an exercise **/
    @Override
    public void saveExercise(int userId, Exercise exercise) {
        Session currentSession = sessionFactory.getCurrentSession();

        User user = getUser(userId);

        exercise.setConnectedWorkout(null);
        user.addExercise(exercise);

        currentSession.saveOrUpdate(exercise);
    }

    /** Add existing exercise to workout (user is already set) **/
    @Override
    public void addExerciseToWorkout(int exerciseId, int workoutId) {
        Session currentSession = sessionFactory.getCurrentSession();

        Workout workout = getWorkout(workoutId);
        Exercise exercise = getExercise(exerciseId);

        exercise.setConnectedWorkout(workout);
        workout.addExercise(exercise);

        currentSession.saveOrUpdate(exercise);
    }

    /** Create a new exercise and add it to a workout as one operation **/
    @Override
    public void addNewExerciseToWorkout(int userId, int workoutId, Exercise exercise) {
        Session currentSession = sessionFactory.getCurrentSession();

        User user = getUser(userId);
        Workout workout = getWorkout(workoutId);

        exercise.setConnectedWorkout(workout);
        user.addExercise(exercise);
        workout.addExercise(exercise);

        currentSession.saveOrUpdate(exercise);
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

}
