package com.ayushsingh.bloggingapplication.payloads;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse<T> {
    T data;
    String status;
    String code;
    public SuccessResponse(){

    }
    public SuccessResponse(String code,String status,T ob){
        data=ob;
        this.status=status;
        this.code=code;
    }
}
