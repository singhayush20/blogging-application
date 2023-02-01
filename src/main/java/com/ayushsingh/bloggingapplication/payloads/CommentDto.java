package com.ayushsingh.bloggingapplication.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

    private int commentId;
    @NotBlank(message = "Comment cannot be empty!")
    @Size(max = 200, message = "Length cannot be greater than 200")
    private String content;

}
