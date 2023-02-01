package com.ayushsingh.bloggingapplication.exceptions;

import lombok.Getter;

@Getter
public class DuplicateResourceException extends RuntimeException {
    private String resourceName;
    private String fieldName;
    private String fieldValue;
    public DuplicateResourceException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s with %s : %s",resourceName,fieldName,fieldValue+" already exists"));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
