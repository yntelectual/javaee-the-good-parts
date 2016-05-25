package com.example.javaee.SampleProjectJAXRS.rest.endpoints;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.example.javaee.SampleProjectJAXRS.cdi.Trace;
import com.example.javaee.SampleProjectJAXRS.dao.BookDAO;
import com.example.javaee.SampleProjectJAXRS.model.Book;
import com.example.javaee.SampleProjectJAXRS.util.ServiceException;

@Path("/books")
@RequestScoped
@Trace
public class BooksEndpoint {

    @Inject
    private BookDAO dao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public List<Book> getAll() {
        return dao.getAll();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Book getBookById(@PathParam("id") long id) {
        return dao.getById(id);
    }
    
    @GET
    @Produces("text/abbreviation")
    @Path("/{id}")
    public String getBookNameAbbrById(@PathParam("id") long id) {
        return dao.getById(id).getName();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Book createBook(Book book) {
       dao.save(book);
       return book;
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Book replaceBook(Book book) {
       return dao.update(book);
    }
    
    @GET
    @Path("/secret")
    @Produces(MediaType.APPLICATION_JSON)
    public Book getSecretBook() throws ServiceException {
        throw new ServiceException("NOT_WORTHY","Only the worthy one can access the secret book");
    }
}
