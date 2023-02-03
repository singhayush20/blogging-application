package com.ayushsingh.bloggingapplication.security;

import java.io.IOException;


import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component //so that we can autowire this class
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
    /**
     * This method is called when unauthorized user 
     * tries to access the authorized apis
     */
    // @Override
    // public void commence(HttpServletRequest request, HttpServletResponse response,
    //         AuthenticationException authException) throws IOException, ServletException {
    //             System.out.println("inside Entry point commence method: user is unauthorized");
    //             //following response will be sent when a user is unauthorized 
    //             //to access the apis
    //     response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied!");
        
    // }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
                System.out.println("inside Entry point commence method: user is unauthorized");
                //following response will be sent when a user is unauthorized 
                //to access the apis
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied!");
        
    }
    
}
