package com.example.javaee.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
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
        // return null;
        return em.createQuery("from Book b", Book.class).getResultList();
    }

    public List<Book> getAllWithReviews() {
        // TODO: implement loading of all books along with all reviews
        return em.createQuery("from Book b left join fetch b.reviews", Book.class).getResultList();
    }

    public Book loadById(String isbn) {
        // TODO: load a single book with all its attributes
        // return null;
        return em.createQuery("select b from Book b left join fetch b.tags left join fetch b.reviews left join fetch b.author left join fetch b.publisher where b.isbn=:isbn", Book.class).setParameter("isbn", isbn).getSingleResult();
    }

    public void persist(Book u) {
        // TODO: insert a new book entry to DB
        em.persist(u);
    }

    public Book update(Book u) {
        // TODO: update existing book entry and return the new state
        // return null;
        return em.merge(u);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Book getById(String isbn) {
        try {
            return em.createQuery("select b from Book b where b.isbn=:isbn", Book.class).setParameter("isbn", isbn).getSingleResult();
        } catch (EntityNotFoundException e) {
            return null;
        }
    }

}
