package com.example.javaee.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.javaee.cdi.Trace;
import com.example.javaee.model.Review;

@Stateless
@Trace
public class ReviewDAO {

    // TODO: get an instance of entity manager here somehow
    @PersistenceContext
    private EntityManager em;

    public List<Review> getAllForBook(String isbn) {
        // TODO: implement loading of all Reviews for a book
        return null;
    }

    public void persist(Review u) {
        // TODO: insert a new Review entry to DB
    }

    public Review update(Review u) {
        // TODO: update existing Review entry and return the new state
        return null;
    }
}
