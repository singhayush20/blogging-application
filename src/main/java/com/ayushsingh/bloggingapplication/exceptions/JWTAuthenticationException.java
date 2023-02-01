package com.ayushsingh.bloggingapplication.exceptions;


import lombok.Getter;

@Getter
public class JWTAuthenticationException extends RuntimeException {
    private String status;
    private String message;
    public JWTAuthenticationException(String status,String message){
        this.status=status;
        this.message=message;
    }
}
