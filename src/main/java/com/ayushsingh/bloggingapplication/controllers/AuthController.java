package com.ayushsingh.bloggingapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.bloggingapplication.exceptions.APIException;
import com.ayushsingh.bloggingapplication.payloads.JWTAuthRequest;
import com.ayushsingh.bloggingapplication.payloads.JWTAuthResponse;
import com.ayushsingh.bloggingapplication.payloads.UserDto;
import com.ayushsingh.bloggingapplication.security.JwtTokenHelper;
import com.ayushsingh.bloggingapplication.services.UserService;

@RestController
@RequestMapping("api/v1/auth/")
public class AuthController {
    // to generate the token
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    // user details
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;
    // authentication manager will be used to authenticate the
    // password
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> createToken(
        @RequestBody JWTAuthRequest request
    ) throws Exception{
        this.authenticate(request.getUsername(),request.getPassword());
        //If authentication is successfull, generate the token
        UserDetails userDetails=this.userDetailsService.loadUserByUsername(request.getUsername());
        String generatedToken=this.jwtTokenHelper.generateToken(userDetails);
        //send this token in the response
        JWTAuthResponse response=new JWTAuthResponse();
        response.setToken(generatedToken);
        return new  ResponseEntity<JWTAuthResponse>(response,HttpStatus.OK);
    }

    private void authenticate(String username, String password) throws Exception {

        // Create authentication token
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
                password);
        // authenticate using authentication manager
        try{
            // this can throw excptions which will be handled globally 
        //in cases like user is disabled
        this.authenticationManager.authenticate(authenticationToken);
        }
        catch(BadCredentialsException e){
            System.out.println("Invalid credentials: username: "+username+" password: "+password);
            //throw the exception to break normal execution
            throw new APIException("Invalid username or password");
        }

    }

    //Register new user api
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        UserDto registeredUser=this.userService.registerNewUser(userDto);
        return new ResponseEntity<UserDto>(registeredUser,HttpStatus.OK);
    }

}
