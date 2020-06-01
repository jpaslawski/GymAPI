package com.example.demo.dao.user;

import com.example.demo.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public User getUserFromToken(String header) {

        // Parse token trough signing key
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey("iFuZc|_6D{UBn(A".getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(header.replace("Bearer ", ""));

        return getUserByEmail(claimsJws.getBody().get("email").toString());
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
}
