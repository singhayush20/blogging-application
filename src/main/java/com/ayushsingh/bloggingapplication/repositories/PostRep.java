package com.ayushsingh.bloggingapplication.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ayushsingh.bloggingapplication.entities.Category;
import com.ayushsingh.bloggingapplication.entities.Post;
import com.ayushsingh.bloggingapplication.entities.User;

public interface PostRep extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    // List<Post> findByCategory(Category category);
    Page<Post> findByCategory(Category category,Pageable page);

    List<Post> findByTitleContaining(String title);

    // search using your own query
    // key- value will be injected %keyword%
    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitleContaining(@Param("key") String title);

}
