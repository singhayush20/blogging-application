package com.ayushsingh.bloggingapplication.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ayushsingh.bloggingapplication.entities.Comment;
import com.ayushsingh.bloggingapplication.entities.Post;

public interface CommentRep extends JpaRepository<Comment,Integer>{
    

    List<Comment> findByPost(Post post);

}
