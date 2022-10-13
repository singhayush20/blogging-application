package com.ayushsingh.bloggingapplication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.bloggingapplication.payloads.ApiResponse;
import com.ayushsingh.bloggingapplication.payloads.PostDto;
import com.ayushsingh.bloggingapplication.payloads.PostResponse;
import com.ayushsingh.bloggingapplication.services.PostService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    // create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
            @PathVariable(name = "userId") Integer uid, @PathVariable(name = "categoryId") Integer categoryId) {
        PostDto newPost = this.postService.createPost(postDto, uid, categoryId);
        return new ResponseEntity<PostDto>(newPost, HttpStatus.CREATED);
    }

    //get post by user id
    @GetMapping(value = "/user/{userId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByUser(
            @PathVariable(name = "userId") Integer userId) {
        List<PostDto> posts = this.postService.getPostsByUser(userId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    //get post by category id
    @GetMapping(value = "/category/{categoryId}/posts")
    public ResponseEntity<List<PostDto>> getPostsByCategory(
            @PathVariable(name = "categoryId") Integer categoryId) {
        List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
        return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
    }

    //get all posts
    @GetMapping(value = "/allposts")//page number starts from 0
    public ResponseEntity<PostResponse> getAllPosts(
        @RequestParam(value="pageNumber",defaultValue = "0",required = false) Integer pageNumber,
        @RequestParam(value="pageSize",defaultValue = "4",required = false) Integer pageSize,
        @RequestParam(value="sortBy",defaultValue="postId",required = false) String sortBy,
        @RequestParam(value="sortDirection",defaultValue = "asc",required = false) String sortDirection)
    {
        PostResponse postResponse = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDirection);
        return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
    }

    //get post by id
    @GetMapping(value = "/{postId}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name = "postId") Integer postId) {
        PostDto post = this.postService.getPostById(postId);
        return new ResponseEntity<PostDto>(post, HttpStatus.OK);
    }

    //delete post
    @DeleteMapping(value="/delete/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable(name="postId") Integer postId){
        this.postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("post deleted successfully",true),HttpStatus.OK);
    }

    //update post
    @PutMapping(value="/update/{postId}")
    public ResponseEntity<PostDto> updatePost(
        @PathVariable(name="postId") Integer postId,
        @RequestBody PostDto postDto){

        PostDto updatedPost=this.postService.updatePost(postDto, postId);
        return new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);
    }

    //get post by matching title 
    @GetMapping(value="/findbystring/{string}")
    public ResponseEntity<List<PostDto>> findPostsByString(@PathVariable(name="string") String string){
        List<PostDto> posts=this.postService.getPostByTitleContaining(string);
        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
    }
}
