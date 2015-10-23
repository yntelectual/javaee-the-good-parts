package com.example.javaee.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;

import com.example.javaee.cdi.Trace;
import com.example.javaee.model.Book;

@Stateless
@Trace
public class BookDAO {

    @Inject
    private Logger logger;

    // TODO: get an instance of entity manager here somehow
    @PersistenceContext
    private EntityManager em;

    public List<Book> getAll() {
        logger.info("loading all");
        // TODO: implement loading of all books without authors and publishers
         return null;
    }

    public List<Book> getAllWithReviews() {
        // TODO: implement loading of all books along with all reviews
        return null;
    }

    public Book loadById(String isbn) {
        // TODO: load a single book with all its attributes
         return null;
    }

    public void persist(Book u) {
        // TODO: insert a new book entry to DB
    }

    public Book update(Book u) {
        // TODO: update existing book entry and return the new state
         return null;
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Book getById(String isbn) {
        return null;
    }

}
