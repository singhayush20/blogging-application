package com.ayushsingh.bloggingapplication.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ayushsingh.bloggingapplication.configs.Appconstants;
import com.ayushsingh.bloggingapplication.constants.AppConstants;
import com.ayushsingh.bloggingapplication.payloads.PostDto;
import com.ayushsingh.bloggingapplication.payloads.PostResponse;
import com.ayushsingh.bloggingapplication.payloads.SuccessResponse;
import com.ayushsingh.bloggingapplication.services.FileService;
import com.ayushsingh.bloggingapplication.services.PostService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/blog/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    // create
    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<PostDto>> createPost(@RequestBody PostDto postDto,
            @RequestParam(name = "userid") Integer uid, @RequestParam(name = "categoryid") Integer categoryId) {
        PostDto newPost = this.postService.createPost(postDto, uid, categoryId);
        SuccessResponse<PostDto> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, newPost);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    // get post by user id
    @GetMapping(value = "/get-post-by-user")
    public ResponseEntity<SuccessResponse<List<PostDto>>> getPostsByUser(
            @RequestParam(name = "userid") Integer userId) {
        List<PostDto> posts = this.postService.getPostsByUser(userId);

        SuccessResponse<List<PostDto>> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, posts);

        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    // get post by category id
    @GetMapping(value = "/get-post-by-category")
    public ResponseEntity<SuccessResponse<List<PostDto>>> getPostsByCategory(
            @RequestParam(name = "categoryid") Integer categoryId) {
        List<PostDto> posts = this.postService.getPostsByCategory(categoryId);
        SuccessResponse<List<PostDto>> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, posts);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    // get all posts
    @GetMapping(value = "/allposts") // page number starts from 0
    public ResponseEntity<SuccessResponse<PostResponse>> getAllPosts(
            @RequestParam(value = "pageNumber", defaultValue = Appconstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = Appconstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = Appconstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = Appconstants.SORT_DIR, required = false) String sortDirection) {
        PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDirection);
        SuccessResponse<PostResponse> successResponse = new SuccessResponse<PostResponse>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, postResponse);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    // get post by id
    @GetMapping(value = "/get-post-by-id")
    public ResponseEntity<SuccessResponse<PostDto>> getPostById(@RequestParam(name = "postid") Integer postId) {
        PostDto post = this.postService.getPostById(postId);
        SuccessResponse<PostDto> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, post);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    // delete post
    @DeleteMapping(value = "/delete")
    public ResponseEntity<SuccessResponse<String>> deletePost(@RequestParam(name = "postid") Integer postId) {
        this.postService.deletePost(postId);
        SuccessResponse<String> successResponse = new SuccessResponse<String>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, "Post deleted successfully");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    // update post
    @PutMapping(value = "/update")
    public ResponseEntity<SuccessResponse<PostDto>> updatePost(
            @RequestParam(name = "postid") Integer postId,
            @RequestBody PostDto postDto) {

        PostDto updatedPost = this.postService.updatePost(postDto, postId);
        SuccessResponse<PostDto> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, updatedPost);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    // search
    @GetMapping(value = "/findbytitle/{string}")
    public ResponseEntity<SuccessResponse<List<PostDto>>> findPostsByTitle(
            @PathVariable(name = "string") String string) {
        List<PostDto> posts = this.postService.searchPosts(string);
        SuccessResponse<List<PostDto>> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, posts);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping(value = "/findBytitle/query/{keyword}")
    public ResponseEntity<SuccessResponse<List<PostDto>>> findPostByTitleQuery(
            @PathVariable(name = "keyword") String keyword) {
        List<PostDto> posts = this.postService.searchByTitle(keyword);
        SuccessResponse<List<PostDto>> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, posts);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    /*----------------------------------------------------------------------------------------------------------------- */
    // post image upload
    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDto> uploadPostImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable(name = "postId") Integer postId) throws IOException {
        // image file name of the file being uploaded
        String fileName;
        PostDto postDto, updatedPostDto;
        // first find the post
        postDto = this.postService.getPostById(postId);

        fileName = this.fileService.uploadImage(path, image);

        postDto.setImage(fileName);

        updatedPostDto = this.postService.updatePost(postDto, postId);

        return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);

    }

    @GetMapping(value = "post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(
            @PathVariable(name = "imageName") String imageName,
            HttpServletResponse response) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);

        StreamUtils.copy(resource, response.getOutputStream());
    }

}
