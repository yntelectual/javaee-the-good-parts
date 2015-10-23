package com.example.javaee.model;

import javax.persistence.Embeddable;

/**
 * A simple address object
 * 
 * @author Matus Majchrak
 * 
 * TODO: addresses should not be stored in separate table,
 * but rather included in the table of the owning entity
 */
@Embeddable
public class Address {

    private String city;

    private String street;

    private String postalCode;

    private String country;

    public Address() {

    }

    public Address(String city, String street, String postalCode, String country) {
        super();
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

}
