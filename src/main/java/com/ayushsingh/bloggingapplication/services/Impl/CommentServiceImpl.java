package com.ayushsingh.bloggingapplication.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayushsingh.bloggingapplication.entities.Comment;
import com.ayushsingh.bloggingapplication.entities.Post;
import com.ayushsingh.bloggingapplication.entities.User;
import com.ayushsingh.bloggingapplication.exceptions.ResourceNotFoundException;
import com.ayushsingh.bloggingapplication.payloads.CommentDto;
import com.ayushsingh.bloggingapplication.repositories.CommentRep;
import com.ayushsingh.bloggingapplication.repositories.PostRep;
import com.ayushsingh.bloggingapplication.repositories.UserRep;
import com.ayushsingh.bloggingapplication.services.CommentService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRep postRepo;
    @Autowired
    private CommentRep commentRepo;

    @Autowired
    private UserRep userRep;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userid) {

        Post post = this.postRepo.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));
        User user = this.userRep.findById(userid)
                .orElseThrow(() -> new ResourceNotFoundException("user", "userid", userid));
        Comment comment = this.modelMapper.map(commentDto, Comment.class);
        comment.setPost(post);// set the post
        comment.setUser(user);// set the user
        Comment savedComment = this.commentRepo.save(comment);
        return this.modelMapper.map(savedComment, CommentDto.class);
    }

    @Override
    public void deleteComment(Integer commentId) {

        Comment comment = this.commentRepo.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "comment id", commentId));
        this.commentRepo.delete(comment);

    }

    @Override
    public List<CommentDto> findByPost(Integer postId) {
        Optional<Post> post = this.postRepo.findById(postId);
        if (!post.isPresent()) {
            throw new ResourceNotFoundException("Post", "post id", postId);
        }
        List<Comment> comments = this.commentRepo.findByPost(post.get());
        List<CommentDto> commentDtos = comments.stream()
                .map((comment) -> this.modelMapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
        return commentDtos;
    }

}
