package com.example.demo.dao.customer;

import com.example.demo.dao.customer.CustomerDAO;
import com.example.demo.entity.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Customer> getCustomers() {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // create a query  ... sort by last name
        Query<Customer> theQuery =
                currentSession.createQuery("FROM Customer ORDER BY lastName", Customer.class);

        // return the results
        return theQuery.getResultList();
    }

    @Override
    public void saveCustomer(Customer theCustomer) {

        Session currentSession = sessionFactory.getCurrentSession();

        currentSession.saveOrUpdate(theCustomer);

    }

    @Override
    public Customer getCustomer(int theId) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // now retrieve/read from database using the primary key
        Customer theCustomer = currentSession.get(Customer.class, theId);

        return theCustomer;
    }

    @Override
    public void deleteCustomer(int theId) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();

        // delete object with primary key
        Query theQuery =
                currentSession.createQuery("DELETE FROM Customer WHERE id=:customerId");
        theQuery.setParameter("customerId", theId);

        theQuery.executeUpdate();
    }

}
