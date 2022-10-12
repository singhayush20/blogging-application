package com.ayushsingh.bloggingapplication.payloads;



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

    private String imgName;

    private UserDto user;

    private CategoryDto category;
}
