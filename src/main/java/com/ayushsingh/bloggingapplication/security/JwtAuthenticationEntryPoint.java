package com.ayushsingh.bloggingapplication.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
@Component //so that we can autowire this class
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
    /**
     * This method is called when unauthorized user 
     * tries to access the authorized apis
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
                System.out.println("inside Entry point commence method: user is unauthorized");
                //following response will be sent when a user is unauthorized 
                //to access the apis
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied!");
        
    }
    
}
