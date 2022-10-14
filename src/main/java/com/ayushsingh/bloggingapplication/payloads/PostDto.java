package com.ayushsingh.bloggingapplication.payloads;



import java.util.HashSet;
import java.util.Set;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
    
    private String postId;

    private String title;
    

    private String content;

    private String addDate;

    private String image;

    private UserDto user;

    private CategoryDto category;

    //this will get the comments also when the post is fetched
    //separate api for the comments is not required in this case
    private Set<CommentDto> comments=new HashSet<>();
}
