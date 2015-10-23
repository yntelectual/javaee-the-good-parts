package com.example.javaee.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.javaee.cdi.Trace;
import com.example.javaee.model.Flat;

@Stateless
@Trace
public class FlatDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Flat> getAll() {
        return em.createQuery("from Flat f", Flat.class).getResultList();
    }
    
    public Flat getById(Object id) {
        return em.find(Flat.class, id);
    }

    public void save(Flat f) {
        em.persist(f);
    }

    public Flat update(Flat f) {
        return em.merge(f);
    }
}
