package com.ayushsingh.bloggingapplication.services;

import java.util.List;

import com.ayushsingh.bloggingapplication.payloads.PostDto;
import com.ayushsingh.bloggingapplication.payloads.PostResponse;

public interface PostService {
    
    //create
    PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto, Integer postId);

    //delete
    void deletePost(Integer postId);

    //get all posts
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDirection);

    //get post by id
    PostDto getPostById(Integer postId);

    //get posts by category
    List<PostDto> getPostsByCategory(Integer categoryId);

    //get all posts by user
    List<PostDto> getPostsByUser(Integer userId);

    List<PostDto> getPostByTitleContaining(String title);
}
