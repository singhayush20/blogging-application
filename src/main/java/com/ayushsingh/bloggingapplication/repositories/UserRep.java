package com.ayushsingh.bloggingapplication.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.bloggingapplication.entities.User;

public interface UserRep extends JpaRepository<User,Integer>{
    
    Optional<User> findByEmail(String email);    
}
