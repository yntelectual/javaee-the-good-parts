package com.example.javaee.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.javaee.cdi.Trace;
import com.example.javaee.model.User;

@Stateless
@Trace
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    public List<User> getAll() {
        return em.createQuery("from User u", User.class).getResultList();
    }

    public void save(User u) {
        em.persist(u);
    }

    public User update(User u) {
        return em.merge(u);
    }

}
