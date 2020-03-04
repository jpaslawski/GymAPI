package com.example.demo.dao;

import com.example.demo.entity.User;
import com.example.demo.entity.Workout;
import org.hibernate.Hibernate;
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

    @Override
    public List<User> getUsers() {

        Session currentSession = sessionFactory.getCurrentSession();

        Query<User> theQuery =
                currentSession.createQuery("FROM User ORDER BY id", User.class);

        return theQuery.getResultList();
    }

    @Override
    public void saveUser(User user) {

        Session currentSession = sessionFactory.getCurrentSession();

        currentSession.saveOrUpdate(user);

    }

    @Override
    public User getUser(int id) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using the primary key
        User user = currentSession.get(User.class, id);

        return user;
    }

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

    @Override
    public void deleteUser(int userId) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // delete object with primary key
        Query theQuery =
                currentSession.createQuery("DELETE FROM User WHERE id=:userId");
        theQuery.setParameter("userId", userId);

        theQuery.executeUpdate();
    }


    @Override
    public List<Workout> getWorkouts() {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Workout> theQuery =
                currentSession.createQuery("FROM Workout ORDER BY id", Workout.class);

        return theQuery.getResultList();
    }

    @Override
    public void saveWorkout(Workout workout) {

        Session currentSession = sessionFactory.getCurrentSession();

        currentSession.saveOrUpdate(workout);
    }

    @Override
    public Workout getWorkout(int workoutId) {

        Workout workout = sessionFactory.getCurrentSession().find(Workout.class, workoutId);
        Hibernate.initialize(workout.getAuthor());
        return workout;
    }

    @Override
    public void deleteWorkout(int workoutId) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // delete object with primary key
        Query theQuery =
                currentSession.createQuery("DELETE FROM Workout WHERE id=:workoutId");
        theQuery.setParameter("workoutId", workoutId);

        theQuery.executeUpdate();
    }

}
