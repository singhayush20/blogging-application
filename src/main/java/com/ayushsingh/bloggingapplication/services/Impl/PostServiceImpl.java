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

@Service
public class PostServiceImpl implements PostService {

        @Autowired
        private PostRep postRep;
        @Autowired
        private UserRep userRep;
        @Autowired
        private CategoryRep catRep;

        @Autowired
        private ModelMapper modelMapper;

        @Override
        public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

                User user = this.userRep.findById(userId)
                                .orElseThrow(() -> new ResourceNotFoundException("user", "user id", userId));
                Category category = this.catRep.findById(categoryId)
                                .orElseThrow(() -> new ResourceNotFoundException("category", "category id",
                                                categoryId));
                Post post = this.modelMapper.map(postDto, Post.class);
                post.setImage(postDto.getImage());
                post.setAddDate(new Date());
                // post.setUser(postDto.getUser());
                // post.setCategory(postDto.getCategory());
                post.setUser(user);
                post.setCategory(category);
                post.setTitle(postDto.getTitle());
                post.setContent(postDto.getContent());
                // save to the database
                Post newPost = this.postRep.save(post);
                return this.modelMapper.map(newPost, PostDto.class);

        }

        @Override
        public PostDto updatePost(PostDto postDto, Integer postId) {
                Post post = this.postRep.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

                post.setContent(postDto.getContent());
                post.setImage(postDto.getImage());
                post.setTitle(postDto.getTitle());

                Post updatedPost=this.postRep.save(post);

                return this.modelMapper.map(updatedPost, PostDto.class);

        }

        @Override
        public void deletePost(Integer postId) {
                Post post = this.postRep.findById(postId)
                                .orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
                this.postRep.delete(post);

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

}
