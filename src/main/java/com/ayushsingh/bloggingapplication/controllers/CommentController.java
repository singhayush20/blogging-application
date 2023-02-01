package com.ayushsingh.bloggingapplication.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import com.ayushsingh.bloggingapplication.constants.AppConstants;
import com.ayushsingh.bloggingapplication.payloads.CommentDto;
import com.ayushsingh.bloggingapplication.payloads.SuccessResponse;
import com.ayushsingh.bloggingapplication.services.CommentService;
import java.util.List;
@RestController
@RequestMapping("/blog/comments")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<SuccessResponse<CommentDto>> createComment(@RequestBody CommentDto commentDto,
            @RequestParam(name = "postid") Integer postId) {
                CommentDto comment = this.commentService.createComment(commentDto, postId);
                SuccessResponse<CommentDto> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE, AppConstants.SUCCESS_MESSAGE, comment);

        return new ResponseEntity<SuccessResponse<CommentDto>>(successResponse, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<SuccessResponse<String>> deleteComment(@RequestParam(name = "commentid") Integer commentId) {

        this.commentService.deleteComment(commentId);
        SuccessResponse<String> successResponse=new SuccessResponse<String>(AppConstants.SUCCESS_CODE, AppConstants.SUCCESS_MESSAGE, "Comment deleted successfully");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/get-comments-by-post")
    public ResponseEntity<SuccessResponse<List<CommentDto>>> commentDto(@RequestParam("postid") Integer postid){
        List<CommentDto> comments=this.commentService.findByPost(postid);
        SuccessResponse<List<CommentDto>> successResponse=new SuccessResponse<>(AppConstants.SUCCESS_CODE, AppConstants.SUCCESS_MESSAGE, comments);

        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }
}
