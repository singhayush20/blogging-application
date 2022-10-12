package com.ayushsingh.bloggingapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.bloggingapplication.entities.Category;

public interface CategoryRep extends JpaRepository<Category,Integer> {
    
}
