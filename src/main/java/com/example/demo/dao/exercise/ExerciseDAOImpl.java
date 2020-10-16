package com.example.demo.dao.exercise;

import com.example.demo.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ExerciseDAOImpl implements ExerciseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /** Get a list of all exercises **/
    @Override
    public List<Exercise> getExercises(User user) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Exercise> theQuery =
                currentSession.createQuery("FROM Exercise WHERE author=:user", Exercise.class);
        theQuery.setParameter("user", user);

        return theQuery.getResultList();
    }

    @Override
    public List<Exercise> getPublicExercises() {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Exercise> theQuery =
                currentSession.createQuery("FROM Exercise WHERE status=0", Exercise.class);
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
    public void saveExercise(User user, Exercise exercise, String category) {
        Session currentSession = sessionFactory.getCurrentSession();

        exercise.setExerciseCategory(getCategoryByName(category));
        exercise.setAuthor(user);
        user.addExercise(exercise);

        currentSession.save(exercise);
    }

    @Override
    public void updateExercise(Exercise exercise, String category) {
        Session currentSession = sessionFactory.getCurrentSession();
        exercise.setExerciseCategory(getCategoryByName(category));

        currentSession.merge(exercise);
    }

    /** Add existing exercise to workout (user is already set) **/
    @Override
    public void addExistingExerciseToWorkout(Exercise exercise, Workout workout) {
        Session currentSession = sessionFactory.getCurrentSession();

        workout.addExercise(exercise);
        workout.setExerciseAmount(workout.getExerciseAmount() + 1);

        currentSession.saveOrUpdate(workout);
    }

    /** Create a new exercise and add it to a workout as one operation **/
    @Override
    public void addNewExerciseToWorkout(User user, Workout workout, Exercise exercise, String category) {
        Session currentSession = sessionFactory.openSession();
        currentSession.beginTransaction();

        exercise.setExerciseCategory(getCategoryByName(category));
        user.addExercise(exercise);
        currentSession.save(exercise);
        currentSession.getTransaction().commit();

        currentSession.getTransaction().begin();
        currentSession.load(Workout.class,workout.getId());
        workout.addExercise(exercise);
        workout.setExerciseAmount(workout.getExerciseAmount() + 1);
        currentSession.update(workout);
        currentSession.getTransaction().commit();
        currentSession.close();

    }

    /** Get an exercise using its ID **/
    @Override
    public Exercise getExercise(int exerciseId) {
        return sessionFactory.getCurrentSession().find(Exercise.class, exerciseId);
    }

    /** Get the last exercise of a user **/
    @Override
    public Exercise getLastExercise(User user) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<ExerciseLog> logQuery =
                currentSession.createQuery("FROM ExerciseLog WHERE trainee=:user ORDER BY id DESC", ExerciseLog.class);
        logQuery.setParameter("user", user);

        ExerciseLog exerciseLog = logQuery.setMaxResults(1).getSingleResult();

        Exercise exercise = sessionFactory.getCurrentSession().find(Exercise.class, exerciseLog.getReferredExercise().getId());

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

    @Override
    public List<ExerciseLog> getExerciseLogs(Exercise exercise, User user) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<ExerciseLog> theQuery =
                currentSession.createQuery("FROM ExerciseLog WHERE trainee=:user AND referredExercise=:exercise ORDER BY id DESC", ExerciseLog.class);
        theQuery.setParameter("user", user);
        theQuery.setParameter("exercise", exercise);

        return theQuery.getResultList();
    }

    @Override
    public void addExerciseLog(User user, ExerciseLog exerciseLog) {
        Session currentSession = sessionFactory.getCurrentSession();

        Exercise exercise = exerciseLog.getReferredExercise();

        exercise.addExerciseLog(exerciseLog);
        user.addExerciseLog(exerciseLog);

        currentSession.saveOrUpdate(exerciseLog);
    }
}
