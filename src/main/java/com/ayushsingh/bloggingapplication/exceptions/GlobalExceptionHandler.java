package com.ayushsingh.bloggingapplication.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArgsNotValidException(MethodArgumentNotValidException ex){
        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error)->{
           String fieldName= ((FieldError)error).getField();
           String errorMessage=error.getDefaultMessage();
           errors.put(fieldName,errorMessage);
        });

        return new ResponseEntity<Map<String,String>>(errors,HttpStatus.BAD_REQUEST);
    }
}
