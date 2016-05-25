package com.example.javaee.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.javaee.model.Measurement;

@Stateless
public class MetricsDAO {

    @PersistenceContext
    private EntityManager em;


    public void save(Measurement u) {
        em.persist(u);
    }
    
    public List<Measurement> getAll() {
        return em.createQuery("from Measurement m", Measurement.class).getResultList();
    }

}
