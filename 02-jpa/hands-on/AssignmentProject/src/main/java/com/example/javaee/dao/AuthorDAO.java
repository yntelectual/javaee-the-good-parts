package com.example.javaee.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.javaee.cdi.Trace;
import com.example.javaee.model.Author;

@Stateless
@Trace
public class AuthorDAO {

    // TODO: get an instance of entity manager here somehow
    @PersistenceContext
    private EntityManager em;

    public List<Author> getAll() {
        return em.createQuery("from Author a", Author.class).getResultList();
    }

    public Author loadById(Long id) {
        // TODO: load a single author with all its attributes
        return em.createQuery("from Author a left join fetch a.books where a.id=:id", Author.class).setParameter("id", id).getSingleResult();
    }

    public void persist(Author u) {
        // TODO: insert a new author entry to DB
        em.persist(u);
    }

    public Author update(Author u) {
        // TODO: update existing author entry and return the new state
        return em.merge(u);
    }
}
