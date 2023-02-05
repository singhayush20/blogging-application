package com.ayushsingh.bloggingapplication.services.Impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ayushsingh.bloggingapplication.constants.AppConstants;
import com.ayushsingh.bloggingapplication.entities.Category;
import com.ayushsingh.bloggingapplication.entities.Post;
import com.ayushsingh.bloggingapplication.entities.User;
import com.ayushsingh.bloggingapplication.exceptions.ResourceNotFoundException;
import com.ayushsingh.bloggingapplication.payloads.PostDto;
import com.ayushsingh.bloggingapplication.payloads.PostResponse;
import com.ayushsingh.bloggingapplication.repositories.CategoryRep;
import com.ayushsingh.bloggingapplication.repositories.PostRep;
import com.ayushsingh.bloggingapplication.repositories.UserRep;
import com.ayushsingh.bloggingapplication.services.PostService;
import com.ayushsingh.bloggingapplication.util.ImageUtil.MyBlobService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.mail.Multipart;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;
import com.ayushsingh.bloggingapplication.exceptions.BlobException;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

        @Autowired
        private PostRep postRep;
        @Autowired
        private UserRep userRep;
        @Autowired
        private CategoryRep catRep;

        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private MyBlobService blobService;

        @Autowired
        private ObjectMapper objectMapper;

        // create post with title and content
        @Override
        public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

                User user = this.userRep.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("user", "user id", userId));
                Category category = this.catRep.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("category", "category id",
                                                categoryId));
                Post post = this.modelMapper.map(postDto, Post.class);

                Post newPost = null;
                // save image
                // if (file != null) {
                // log.info("Filename :" + file.getOriginalFilename());
                // log.info("Size:" + file.getSize());
                // log.info("Contenttype:" + file.getContentType());
                // try {

                // blobService.storeFile(file.getOriginalFilename(), file.getInputStream(),
                // file.getSize());
                // post.setImage(file.getOriginalFilename());
                post.setAddDate(new Date());
                post.setUser(user);
                post.setCategory(category);
                post.setTitle(postDto.getTitle());
                post.setContent(postDto.getContent());
                // save to the database
                newPost = this.postRep.save(post);

                // } catch (IOException ex) {
                // throw new BlobException(AppConstants.ERROR_CODE, AppConstants.ERROR_MESSAGE,
                // file.getOriginalFilename() + " could not be saved!");
                // }

                // }

                return this.modelMapper.map(newPost, PostDto.class);

        }

        // upload an image to the post
        @Override
        public String uploadImage(MultipartFile file, int postid, boolean isUpdatingPost) {
                Post post = postRep.findById(postid)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postid));

                // if the post is getting updated
                if (isUpdatingPost == true) {
                        // if some previous image is set, then delete it first
                        if (post.getImage() != null) {
                                blobService.deleteFile(post.getImage());
                        }
                }
                if (file != null) {
                        log.info("Filename :" + file.getOriginalFilename());
                        log.info("Size:" + file.getSize());
                        log.info("Contenttype:" + file.getContentType());
                        try {

                                String result = blobService.storeFile(file.getOriginalFilename(), file.getInputStream(),
                                                file.getSize());
                                post.setImage(file.getOriginalFilename());
                                postRep.save(post);
                                return result;

                        } catch (IOException ex) {
                                throw new BlobException(AppConstants.ERROR_CODE, AppConstants.ERROR_MESSAGE,
                                                file.getOriginalFilename() + " could not be saved!");
                        }
                }
                return "Image not found";
        }

        //update post content
        @Override
        public PostDto updatePost(PostDto postDto, Integer postId) {
                Post post = this.postRep.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

                post.setContent(postDto.getContent());
                // post.setImage(postDto.getImage()); //image should be set when uploaded
                post.setTitle(postDto.getTitle());

                Post updatedPost = this.postRep.save(post);

                return this.modelMapper.map(updatedPost, PostDto.class);

        }

        // delete a post
        @Override
        public String deletePost(Integer postId) {
                boolean imageDeleted = false, postDeleted = false;
                Post post = this.postRep.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
                if (post.getImage() != null) {
                        imageDeleted = blobService.deleteFile(post.getImage());
                } else
                        imageDeleted = true;
                this.postRep.delete(post);
                postDeleted = true;
                return "Post deleted: " + postDeleted + "Image deleted: " + imageDeleted;

        }
        @Override
        public String deleteImage(int postId){
                Post post = this.postRep.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
                if(post.getImage()!=null){
                        boolean result=blobService.deleteFile(post.getImage());
                        if(result==true){
                                post.setImage(null);
                                postRep.save(post);
                        }
                        return "Image deleted!";
                }
                return "Post does not have an image";
        }

        @Override
        public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
                Sort sort = null;
                // Create pageable object
                if (sortDirection.equalsIgnoreCase("asc")) {
                        sort = Sort.by(sortBy).ascending();
                } else if (sortDirection.equalsIgnoreCase("desc")) {
                        sort = Sort.by(sortBy).descending();
                }
                Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

                // Get the required page
                Page<Post> page = this.postRep.findAll(pageable);
                // Get the list of posts
                List<Post> posts = page.getContent();

                List<PostDto> newPosts = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                                .collect(Collectors.toList());

                PostResponse postResponse = new PostResponse();
                postResponse.setContent(newPosts);
                postResponse.setPageNumber(page.getNumber());
                postResponse.setPageSize(page.getSize());
                postResponse.setLastPage(page.isLast());
                postResponse.setTotalElements(page.getTotalElements());
                postResponse.setTotalPages(page.getTotalPages());
                postResponse.setCurrentPageSize(newPosts.size());
                return postResponse;
        }

        @Override
        public PostDto getPostById(Integer postId) {

                Post post = this.postRep.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

                return this.modelMapper.map(post, PostDto.class);
        }

        @Override
        public List<PostDto> getPostsByCategory(Integer categoryId) {
                Category category = this.catRep.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("category", "cateogory id",
                                                categoryId));
                List<Post> posts = this.postRep.findByCategory(category);
                List<PostDto> newPosts = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                                .collect(Collectors.toList());
                return newPosts;
        }

        @Override
        public List<PostDto> getPostsByUser(Integer userId) {
                User user = this.userRep.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("user", "user id", userId));
                List<Post> posts = this.postRep.findByUser(user);
                List<PostDto> newPosts = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                                .collect(Collectors.toList());
                return newPosts;
        }

        @Override
        public List<PostDto> searchPosts(String keyword) {
                if (keyword == null || keyword.isBlank()) {
                        throw new ResourceNotFoundException("post with title containing string ", keyword, 0);
                }
                List<Post> posts = this.postRep.findByTitleContaining(keyword);
                List<PostDto> newPosts = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                                .collect(Collectors.toList());
                return newPosts;
        }

        // search using query
        @Override
        public List<PostDto> searchByTitle(String keyword) {

                if (keyword == null || keyword.isBlank()) {
                        throw new ResourceNotFoundException("post with title containing string ", keyword, 0);
                }
                List<Post> posts = this.postRep.searchByTitleContaining("%" + keyword + "%");
                List<PostDto> newPosts = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
                                .collect(Collectors.toList());
                return newPosts;
        }

        @Override
        public PostDto getJson(String post) {
                PostDto postDto = new PostDto();
                try {
                        postDto = this.objectMapper.readValue(post, PostDto.class);
                } catch (IOException err) {
                        System.out.println("Some error occured while converting post string to post POJO: "
                                        + err.getMessage());
                        err.printStackTrace();
                }
                return postDto;
        }
}
