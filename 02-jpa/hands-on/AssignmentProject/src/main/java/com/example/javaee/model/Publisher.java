package com.example.javaee.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * The book publishing company.
 * 
 * @author Matus 
 * 
 * TODO: make me an entity stored in DB table B_PUBLISHER, with auto generated LONG id.
 * TODO: we want to track the creation/update date of this entity.
 */
@Entity
public class Publisher extends TraceAble {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    //TODO: lazy loaded, with FK in Book table named PUBLISHER_ID
    @OneToMany
    private Set<Book> publishedBooks;

    //TODO: stored within publisher table
    private Address address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<Book> getPublishedBooks() {
        return publishedBooks;
    }

    public void setPublishedBooks(Set<Book> publishedBooks) {
        this.publishedBooks = publishedBooks;
    }

}
