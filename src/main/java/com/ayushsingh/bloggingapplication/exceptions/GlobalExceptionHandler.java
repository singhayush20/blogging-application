package com.ayushsingh.bloggingapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ayushsingh.bloggingapplication.payloads.ApiResponse;

@RestControllerAdvice //this will make the class work like an exception handler
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ResourceNotFoundException.class) //add comma separated list of Exception classes
    public ResponseEntity<ApiResponse> resourceNotFoundException(ResourceNotFoundException ex){
        String message=ex.getMessage();
        ApiResponse apiResponse=new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
