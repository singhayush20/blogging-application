package com.ayushsingh.bloggingapplication.services;

import com.ayushsingh.bloggingapplication.payloads.CommentDto;
import java.util.List;
public interface CommentService {
    
    CommentDto createComment(CommentDto commentDto, Integer postId);

    void deleteComment(Integer commentId);

    List<CommentDto> findByPost(Integer postId);
    
}
