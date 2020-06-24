package com.example.demo.dao.exercise;

import com.example.demo.dao.workout.WorkoutDAOImpl;
import com.example.demo.entity.*;
import com.example.demo.entity.request.ExerciseLogData;
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
    public void saveExercise(User user, Exercise exercise, String category) {
        Session currentSession = sessionFactory.getCurrentSession();

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

        Workout workout = new WorkoutDAOImpl().getWorkout(workoutId);
        Exercise exercise = getExercise(exerciseId);
        workout.addExercise(exercise);
        workout.setExerciseAmount(workout.getExerciseAmount() + 1);

        currentSession.saveOrUpdate(exercise);
    }

    /** Create a new exercise and add it to a workout as one operation **/
    @Override
    public void addNewExerciseToWorkout(User user, int workoutId, Exercise exercise, String category) {
        Session currentSession = sessionFactory.getCurrentSession();

        Workout workout = new WorkoutDAOImpl().getWorkout(workoutId);

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

    @Override
    public List<ExerciseLog> getExerciseLogs(Exercise exercise, User user) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<ExerciseLog> theQuery =
                currentSession.createQuery("FROM ExerciseLog WHERE trainee=:user AND referredExercise=:exercise ORDER BY submitDate DESC", ExerciseLog.class);
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
