package com.example.javaee.SampleProjectJAXRS.rest.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.javaee.SampleProjectJAXRS.model.A;
import com.example.javaee.SampleProjectJAXRS.model.B;

@Path("/misc")
public class MiscEndpoint {

    /**
     * Look ma', it will break the server.
     * @return
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public A getSomeA(){
        A a = new A();
        a.setId(1);
        a.setName("Cant live without B");
        B b = new B();
        b.setA(a);
        b.setName("can live without A");
        a.setB(b);
        return a;
    }
}
