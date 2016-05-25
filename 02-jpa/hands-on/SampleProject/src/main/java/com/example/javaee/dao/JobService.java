package com.example.javaee.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.example.javaee.model.Category;
import com.example.javaee.model.Job;

@Stateless
public class JobService {

    @PersistenceContext
    EntityManager em;
    
    public void persistJob(Job newJob) {
        em.persist(newJob);
    }
    
    public Job getJob(Integer jobId) {
        return em.find(Job.class, jobId);
    }
    
    public void saveJob(Job job) {
        em.merge(job);
    }
    
    public void persistCategory(Category newcat) {
        em.persist(newcat);
    }
    
    public void updateCategory(Category cat) {
        em.merge(cat);
    }
    
    public List<Category> getAllCategories() {
        return em.createQuery("from Category", Category.class).getResultList();
    }
    
}
