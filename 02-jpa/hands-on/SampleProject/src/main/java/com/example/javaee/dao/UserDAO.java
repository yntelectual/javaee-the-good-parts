package com.example.javaee.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

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

    public User getById(Object id) {
        return em.find(User.class, id);
    }

    public User loadById(Object id) {
        TypedQuery<User> q = em.createQuery("select u from User u left join fetch u.cars c left join fetch u.flats f where u.id =:id", User.class);
        q.setParameter("id", id);
        return q.getSingleResult();
    }

    public User update(User u) {
        return em.merge(u);
    }
}
