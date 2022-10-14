package com.ayushsingh.bloggingapplication.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.bloggingapplication.entities.Comment;

public interface CommentRep extends JpaRepository<Comment,Integer>{
    
}
