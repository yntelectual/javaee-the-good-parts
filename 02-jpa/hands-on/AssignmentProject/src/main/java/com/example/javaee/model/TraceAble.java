package com.example.javaee.model;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
@MappedSuperclass
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

    @PostLoad
    @PostUpdate
    public void postLoad() {
        this.loadedFromDB = true;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updateDate = new Date();
    }
    
    @PrePersist
    public void prePersist() {
        this.creationDate = new Date();
        this.updateDate = new Date();
    }
    
}
