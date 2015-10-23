package com.example.javaee.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.javaee.cdi.Trace;
import com.example.javaee.model.Car;

@Stateless
@Trace
public class CarDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Car> getAll() {
        return em.createQuery("from Car c", Car.class).getResultList();
    }
    
    public Car getById(Object id) {
        return em.find(Car.class, id);
    }

    public void save(Car c) {
        em.persist(c);
    }

    public Car update(Car c) {
        return em.merge(c);
    }
}
