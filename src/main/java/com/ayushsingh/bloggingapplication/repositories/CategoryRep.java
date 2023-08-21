package com.ayushsingh.bloggingapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.bloggingapplication.entities.Category;
import java.util.Optional;

public interface CategoryRep extends JpaRepository<Category, Integer> {

    Optional<Category> findByCategoryName(String categoryName);
}
