package com.example.javaee.model;

import java.util.Date;

//TODO we want attribtues from this class to be presetn on every entity that extends it
//TODO you can use the Entity lifecycle event methods here(at the end of the file), or create your one EntityListener
public class TraceAble {

    private Date creationDate;

    private Date updateDate;

    //TODO: automatically set this to true when entity has been loaded from DB
    private boolean loadedFromDB;

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public boolean isLoadedFromDB() {
        return loadedFromDB;
    }

    public void setLoadedFromDB(boolean loadedFromDB) {
        this.loadedFromDB = loadedFromDB;
    }

    public void postLoad() {
       //TODO make me usefull 
    }
    
    public void preUpdate() {
      //TODO make me usefull 
    }
    
    public void prePersist() {
      //TODO make me usefull 
    }
    
}
