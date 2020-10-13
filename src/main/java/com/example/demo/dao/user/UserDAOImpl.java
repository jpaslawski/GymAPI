package com.example.demo.dao.user;

import com.example.demo.entity.User;
import com.example.demo.entity.UserDiet;
import com.example.demo.entity.WeightLog;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUserFromToken(String header) {

        // Get payload from token
        String s1 = header.substring(header.indexOf(".") + 1);
        String s2 = s1.substring(s1.indexOf("."));
        String payloadBase64 = s1.replace(s2, "");

        // Decode payload and extract email
        byte[] decodedPayloadBytes = Base64.getDecoder().decode(payloadBase64.getBytes());
        JSONObject jsonObject = new JSONObject(new String(decodedPayloadBytes));
        String email = jsonObject.getString("email");

        return getUserByEmail(email);
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
    public void saveUser(User user, UserDiet userDiet) {

        Session currentSession = sessionFactory.getCurrentSession();

        currentSession.saveOrUpdate(user);
        currentSession.saveOrUpdate(userDiet);

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

    @Override
    public List<WeightLog> getWeightLogs(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<WeightLog> theQuery =
                currentSession.createQuery("FROM WeightLog WHERE user=:user", WeightLog.class);
        theQuery.setParameter("user", user);

        return theQuery.getResultList();
    }

    @Override
    public WeightLog getCurrentWeight(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        Query<WeightLog> theQuery =
                currentSession.createQuery("FROM WeightLog WHERE user=:user ORDER BY id DESC", WeightLog.class);
        theQuery.setParameter("user", user);

        return theQuery.setMaxResults(1).getSingleResult();
    }

    @Override
    public boolean checkTodayWeightLog(User user) {
        Session currentSession = sessionFactory.getCurrentSession();

        LocalDate now = LocalDate.now();
        Query<WeightLog> theQuery =
                currentSession.createQuery("FROM WeightLog WHERE user=:user AND submitDate=:now", WeightLog.class);
        theQuery.setParameter("user", user);
        theQuery.setParameter("now", now);

        if(theQuery.getResultList().size() != 0) {
            return true;
        }
        return false;
    }

    @Override
    public void saveWeightLog(WeightLog weightLog, User user) {
        Session currentSession = sessionFactory.getCurrentSession();

        user.addWeightLog(weightLog);
        currentSession.saveOrUpdate(weightLog);

        user.setWeight(weightLog.getCurrentWeight());
        currentSession.saveOrUpdate(user);
    }

    @Override
    public UserDiet getUserDietDetails(User user) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<UserDiet> theQuery =
                currentSession.createQuery("FROM UserDiet WHERE user=:user", UserDiet.class);
        theQuery.setParameter("user", user);

        return theQuery.setMaxResults(1).getSingleResult();
    }

    @Override
    public void saveUserDietDetails(UserDiet userDiet) {
        Session currentSession = sessionFactory.getCurrentSession();

        currentSession.saveOrUpdate(userDiet);
    }
}
