package com.ayushsingh.bloggingapplication.payloads;

import lombok.Data;

@Data//for getter and setter
public class JWTAuthResponse {
    private int userId;
    private String token;
    //We can also return when the token was
    //generated
}
