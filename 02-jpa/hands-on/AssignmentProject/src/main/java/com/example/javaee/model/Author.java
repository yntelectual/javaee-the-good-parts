package com.example.javaee.model;

import java.util.Date;
import java.util.Set;

/**
 * The Author entity.
 * 
 * @author Matus
 * 
 * TODO: make this a DB entity with table name B_AUTHOR with auto generated LONG ID.
 * TODO: we want to track creation/update date of this
 * entity
 *
 */
public class Author extends TraceAble {

    private Long id;

    private String name;

    // TODO store this as DB DATE not TIMESTAMP
    private Date dateOfBirth;

    // TODO: should not be stored in DB obviously, but calculated when loaded - currDate - dateOfBirth / ms_in_year
    private long age;

    // TODO: do not load eagerly,
    // TODO: we want a FK column in the Book table named 'AUTHOR_ID'
    // TODO: make it possible to assign book to author by saving author
    private Set<Book> books;

    // TODO: included in the author table
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        this.age = age;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    // TODO use this to calculate the age after load from DB
    public void calcAge() {
        this.age = (new Date().getTime() - this.dateOfBirth.getTime()) / (1000 * 60 * 60 * 24 * 365);
    }
}
