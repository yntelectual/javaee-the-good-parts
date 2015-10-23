package com.example.javaee.model;

/**
 * The Book review entity
 * 
 * @author Matus
 * 
 * TODO: make this a DB entity with table name B_REVIEW with auto generated LONG id.
 * TODO: we want to track creation/update date of this entity
 */
public class Review extends TraceAble {
    private Long id;

    // TODO: we want to store at least 1000 characters here
    private String text;
    
    //TODO lazy loaded book reference, 
    private Book book;

    // String identifier or the user who created the review.
    // TODO: DB wont be very happy with the naming of our attribute, but you manager insists that it has to be named like that in your
    // model..
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
