package com.example.javaee.SampleProjectJAXRS.rest.providers;

import javax.inject.Inject;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

import com.example.javaee.SampleProjectJAXRS.util.ServiceException;


/**
 * Transform any {@link ServiceException} thrown during invocation of REST resources to a useful JSON representation for the client.
 * @author Matus Majchrak
 *
 */
@Provider
public class RSExceptionMapper implements ExceptionMapper<ServiceException> {
    
    @Inject
    private Logger log;

    @Context
    private ResourceInfo resInfo;
    
    @Override
    public Response toResponse(ServiceException exception) {
        log.error("JAXRS exception mapper: ", exception);
        RestExceptionModel model = new RestExceptionModel();
        model.setCode(exception.getErrorKey() != null ? exception.getErrorKey() : "UNKNOWN");
        model.setMessage(exception.getErrorMessage());
        return Response.serverError().entity(model).build();
    }

}
