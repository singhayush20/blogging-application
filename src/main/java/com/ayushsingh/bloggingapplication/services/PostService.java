package com.ayushsingh.bloggingapplication.services;

import java.util.List;

import com.ayushsingh.bloggingapplication.entities.Post;
import com.ayushsingh.bloggingapplication.payloads.PostDto;

public interface PostService {
    
    //create
    PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto, Integer postId);

    //delete
    void deletePost(Integer postId);

    //get all posts
    List<Post> getAllPosts();

    //get post by id
    PostDto getPostById(Integer postId);

    //get posts by category
    List<Post> getPostsByCategory(Integer categoryId);

    //get all posts by user
    List<Post> getPostsByUser(Integer userId);
}
