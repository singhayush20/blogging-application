package com.ayushsingh.bloggingapplication.exceptions;

public class BlobException extends RuntimeException {
    private String code, message, status;

    public BlobException(){

    }
    
   public  BlobException(String code, String status, String message) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }
}
