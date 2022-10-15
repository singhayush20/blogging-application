package com.ayushsingh.bloggingapplication.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ayushsingh.bloggingapplication.entities.User;
import com.ayushsingh.bloggingapplication.exceptions.ResourceNotFoundException;
import com.ayushsingh.bloggingapplication.repositories.UserRep;
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRep userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //loading user from database by username
        User user=this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("user ", "emai: "+username, 0));

        return user;
    }
}
