package com.example.demo.dao.workout;

import com.example.demo.entity.User;
import com.example.demo.entity.Workout;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WorkoutDAOImpl implements WorkoutDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /** Get a list of all the workouts **/
    @Override
    public List<Workout> getWorkouts(User user) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Workout> theQuery =
                currentSession.createQuery("FROM Workout WHERE author=:user", Workout.class);
        theQuery.setParameter("user", user);

        return theQuery.getResultList();
    }

    @Override
    public List<Workout> getPublicWorkouts() {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Workout> theQuery =
                currentSession.createQuery("FROM Workout WHERE isPublic=true", Workout.class);
        return theQuery.getResultList();
    }

    /** Get a workout using its ID **/
    @Override
    public Workout getWorkout(int workoutId) {

        Workout workout = sessionFactory.getCurrentSession().find(Workout.class, workoutId);
        return workout;
    }

    /** Save or update a workout **/
    @Override
    public void saveWorkout(User user, Workout workout) {

        Session currentSession = sessionFactory.getCurrentSession();

        workout.setAuthor(user);
        user.addWorkout(workout);

        currentSession.saveOrUpdate(workout);
    }

    /** Delete a workout by ID **/
    @Override
    public void deleteWorkout(int workoutId) {

        Session currentSession = sessionFactory.getCurrentSession();
        Workout workout = currentSession.find(Workout.class, workoutId);

        workout.getExercises().clear();
        currentSession.remove(workout);
    }
}
