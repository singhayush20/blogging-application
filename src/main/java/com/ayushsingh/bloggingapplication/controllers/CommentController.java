package com.ayushsingh.bloggingapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.bloggingapplication.payloads.ApiResponse;
import com.ayushsingh.bloggingapplication.payloads.CommentDto;
import com.ayushsingh.bloggingapplication.services.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/post/{postId}/comments")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto,
            @PathVariable(name = "postId") Integer postId) {
        CommentDto comment = this.commentService.createComment(commentDto, postId);

        return new ResponseEntity<CommentDto>(comment, HttpStatus.OK);
    }

    @DeleteMapping("post/delete/comments/{commentId}")
    public ResponseEntity<ApiResponse> deleteComment(@PathVariable(name = "commentId") Integer commentId) {

        this.commentService.deleteComment(commentId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("comment deleted successfully", true), HttpStatus.OK);

    }
}
