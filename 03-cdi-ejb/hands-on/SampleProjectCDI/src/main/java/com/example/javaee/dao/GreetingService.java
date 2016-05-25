package com.example.javaee.dao;

import javax.ejb.Stateless;

import com.example.javaee.cdi.Polite;
import com.example.javaee.cdi.Trace;

@Stateless
@Trace
@Polite
public class GreetingService {

    public String sayHi(String name) {
        System.out.println(name);
        return name;
    }
}
