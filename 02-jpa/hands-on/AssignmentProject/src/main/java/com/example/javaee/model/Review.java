package com.example.javaee.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * The Book review entity
 * 
 * @author Matus
 * 
 * TODO: make this a DB entity with table name B_REVIEW with auto generated LONG id.
 * TODO: we want to track creation/update date of this entity
 */
@Entity
public class Review extends TraceAble {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    // TODO: we want to store at least 1000 characters here
    @Column(length=1024)
    private String text;
    
    //TODO lazy loaded book reference, 
    @ManyToOne(fetch=FetchType.LAZY,optional=false)
    private Book book;

    // String identifier or the user who created the review.
    // TODO: DB wont be very happy with the naming of our attribute, but you manager insists that it has to be named like that in your
    // model..
    @Column(name="fromname")
    private String from;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

}
