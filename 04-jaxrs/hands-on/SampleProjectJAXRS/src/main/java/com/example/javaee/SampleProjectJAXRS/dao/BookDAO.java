package com.example.javaee.SampleProjectJAXRS.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.javaee.SampleProjectJAXRS.cdi.Trace;
import com.example.javaee.SampleProjectJAXRS.model.Book;

@Stateless
@Trace
public class BookDAO {

    @PersistenceContext
    private EntityManager em;

    public List<Book> getAll() {
        return em.createQuery("from Book b", Book.class).getResultList();
    }

    public void save(Book u) {
        em.persist(u);
    }

    public Book update(Book u) {
        return em.merge(u);
    }

    public Book getById(long id) {
        return em.createQuery("from Book b where b.id=:id", Book.class).setParameter("id", id).getSingleResult();
    }

}
