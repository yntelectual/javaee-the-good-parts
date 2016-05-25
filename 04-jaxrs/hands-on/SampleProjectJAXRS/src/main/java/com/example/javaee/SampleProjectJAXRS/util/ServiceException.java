package com.example.javaee.SampleProjectJAXRS.util;

public class ServiceException extends Exception {

    private static final long serialVersionUID = -3723735112628081015L;

    private String errorKey;

    private String errorMessage;

    public ServiceException() {
        super();
    }

    public ServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.errorMessage = message;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
    }

    public ServiceException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public ServiceException(String errorKey, String message, Throwable cause) {
        super(message, cause);
        this.errorMessage = message;
        this.errorKey = errorKey;
    }
    
    public ServiceException(String errorKey, String message) {
        super(message);
        this.errorMessage = message;
        this.errorKey = errorKey;
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public String getErrorKey() {
        return errorKey;
    }

    public void setErrorKey(String errorKey) {
        this.errorKey = errorKey;
    }

    public String getErrorMessage() {
        return errorMessage != null ? errorMessage : getMessage();
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
