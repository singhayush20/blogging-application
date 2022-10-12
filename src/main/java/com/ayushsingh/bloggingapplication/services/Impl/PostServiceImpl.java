package com.ayushsingh.bloggingapplication.services.Impl;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayushsingh.bloggingapplication.entities.Category;
import com.ayushsingh.bloggingapplication.entities.Post;
import com.ayushsingh.bloggingapplication.entities.User;
import com.ayushsingh.bloggingapplication.exceptions.ResourceNotFoundException;
import com.ayushsingh.bloggingapplication.payloads.PostDto;
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
    public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {

        User user =this.userRep.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "user id",userId));
        Category category=this.catRep.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category", "category id", categoryId));
        Post post=this.modelMapper.map(postDto,Post.class);
        post.setImgName(postDto.getImgName());
        post.setAddDate(new Date());
        // post.setUser(postDto.getUser());
        // post.setCategory(postDto.getCategory());
        post.setUser(user);
        post.setCategory(category);
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        //save to the database
        Post newPost=this.postRep.save(post);
        return this.modelMapper.map(newPost,PostDto.class);
        
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deletePost(Integer postId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Post> getAllPosts() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public PostDto getPostById(Integer postId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Post> getPostsByCategory(Integer categoryId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Post> getPostsByUser(Integer userId) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
