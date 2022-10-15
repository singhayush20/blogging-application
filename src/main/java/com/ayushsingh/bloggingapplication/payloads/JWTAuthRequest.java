package com.ayushsingh.bloggingapplication.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class JWTAuthRequest {
    private String username;//email is used as username
    private String password;
}
