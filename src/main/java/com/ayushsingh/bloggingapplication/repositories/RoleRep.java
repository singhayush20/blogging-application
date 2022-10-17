package com.ayushsingh.bloggingapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.bloggingapplication.entities.Role;

public interface RoleRep extends JpaRepository<Role,Integer> {
    
}
