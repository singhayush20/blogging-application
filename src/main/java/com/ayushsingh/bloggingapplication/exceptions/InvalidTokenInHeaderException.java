package com.ayushsingh.bloggingapplication.exceptions;

public class InvalidTokenInHeaderException extends RuntimeException{
  
    String message;
    public InvalidTokenInHeaderException(String m){
        message=m;
    }
    @Override
    public String getMessage() {
        return message;
    }    
}
