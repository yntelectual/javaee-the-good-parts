package com.example.javaee.SampleProjectJAXRS.rest.providers;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;

@Provider
public class LoggingFilter implements ContainerRequestFilter{
    
    @Inject
    private Logger log;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        log.info("{} to {}" , requestContext.getMethod(),requestContext.getUriInfo().getRequestUri());
    }

}
