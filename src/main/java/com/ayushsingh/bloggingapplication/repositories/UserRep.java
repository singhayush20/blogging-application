package com.ayushsingh.bloggingapplication.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.bloggingapplication.entities.User;

public interface UserRep extends JpaRepository<User,Integer>{
    //We want repository for the entity User and the id type is integer
    
    
}
