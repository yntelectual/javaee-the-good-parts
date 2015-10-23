package com.example.javaee.cdi;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.slf4j.Logger;

@Singleton
@Startup
public class Beacon {
    
    @Inject
    private Logger log;
    
    @PostConstruct
    public void init(){
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        log.info("@@@@ houston we are ready for lift off @@@@");
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    }
    
    public void sanityCheck() throws Exception {
        //TODO just remove the exception, I only wanted to see if you read the TODOs and TEST results.
       // throw new IllegalStateException("Thrown becuase of total lack of attention");
    }
}
