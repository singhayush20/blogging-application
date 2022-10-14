package com.ayushsingh.bloggingapplication.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayushsingh.bloggingapplication.entities.Comment;
import com.ayushsingh.bloggingapplication.entities.Post;
import com.ayushsingh.bloggingapplication.exceptions.ResourceNotFoundException;
import com.ayushsingh.bloggingapplication.payloads.CommentDto;
import com.ayushsingh.bloggingapplication.repositories.CommentRep;
import com.ayushsingh.bloggingapplication.repositories.PostRep;
import com.ayushsingh.bloggingapplication.services.CommentService;
@Service
public class CommentServiceImpl implements CommentService{
    @Autowired
    private PostRep postRepo;
    @Autowired
    private CommentRep commentRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        
        Post post=this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("post", "post id", postId));
        Comment comment=this.modelMapper.map(commentDto,Comment.class);
        comment.setPost(post);
        Comment savedComment=this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment,CommentDto.class);
    }
    @Override
    public void deleteComment(Integer commentId) {
        
        Comment comment=this.commentRepo.findById(commentId).orElseThrow(()->new ResourceNotFoundException("comment", "comment id", commentId));
        this.commentRepo.delete(comment);
        
        
    }
    
    
}
