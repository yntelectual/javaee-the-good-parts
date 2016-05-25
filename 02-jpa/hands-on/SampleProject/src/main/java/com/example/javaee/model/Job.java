package com.example.javaee.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Size;

@Entity
public class Job {

    @Id
    @GeneratedValue
    private Integer id;

    private String title;

    @ManyToMany(fetch=FetchType.EAGER,cascade = { CascadeType.MERGE })
    @JoinTable(name = "jobs_categories", joinColumns = @JoinColumn(name = "jobs_id") , inverseJoinColumns = @JoinColumn(name = "categories_id") )
    private Set<Category> categories = new HashSet<Category>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

}
