package com.example.javaee.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.javaee.cdi.Trace;
import com.example.javaee.model.Publisher;

@Stateless
@Trace
public class PublisherDAO {

    // TODO: get an instance of entity manager here somehow
    @PersistenceContext
    private EntityManager em;

    public List<Publisher> getAll() {
        //TODO return all publishers
        return null;
    }

    public Publisher loadById(Long id) {
        // TODO: load a single Publisher with all its attributes
        return null;
    }

    public void persist(Publisher u) {
    }

    public Publisher update(Publisher u) {
        // TODO: update existing Publisher entry and return the new state
        return null;
    }
}
