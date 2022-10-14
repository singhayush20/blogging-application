package com.ayushsingh.bloggingapplication.services;

import com.ayushsingh.bloggingapplication.payloads.CommentDto;

public interface CommentService {
    
    CommentDto createComment(CommentDto commentDto, Integer postId);

    void deleteComment(Integer commentId);
    
}
