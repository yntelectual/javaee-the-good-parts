package com.example.javaee.model;

import java.util.Date;
import java.util.Set;

/**
 * The Book entity.
 * 
 * @author Matus
 * 
 * TODO: make this a DB entity with table name B_BOOK with unique constraint on the IBSN which acts as table ID.
 * TODO: we want to track creation/update date of this entity
 */
public class Book extends TraceAble {

    // ISBN is a unique identifier of every book,
    private String isbn;

    private String name;

    // TODO we want the author to be eagerly loaded
    private Author author;

    // TODO: lazy loaded reviews
    private Set<Review> reviews;

    private Publisher publisher;

    private Date publishDate;

    // TODO: load EAGERly, we want a very simple java model with a String set of tags(scifi,novel,drama...)
    private Set<String> tags;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

}
