package com.ayushsingh.bloggingapplication.services;

import java.util.List;

import com.ayushsingh.bloggingapplication.payloads.PostDto;
import com.ayushsingh.bloggingapplication.payloads.PostResponse;
import org.springframework.web.multipart.MultipartFile;

public interface PostService {
    
    //create
    PostDto createPost(PostDto postDto,Integer userId, Integer categoryId);

    //update
    PostDto updatePost(PostDto postDto, Integer postId);

    //delete
    String deletePost(Integer postId);

    //get all posts
    PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy,String sortDirection);

    //get post by id
    PostDto getPostById(Integer postId);

    //get posts by category
    PostResponse getPostsByCategory(Integer categoryId,Integer pageNumber,Integer pageSize,String sortBy, String sortDirection);

    //get all posts by user
    List<PostDto> getPostsByUser(Integer userId);

    //search posts
    List<PostDto> searchPosts(String keyword);

    List<PostDto> searchByTitle(String keyword);

    PostDto getJson(String post);

    String uploadImage(MultipartFile image, int postId, boolean isUpdatingPost);

    String deleteImage(int postid);
}
