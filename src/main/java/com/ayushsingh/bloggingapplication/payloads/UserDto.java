package com.ayushsingh.bloggingapplication.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    @NotBlank(message = "username cannot be empty")
    @Size(min=5,message="username must be greater than 4 characters")
    private String name;
    @Email(message = "Email is not Valid!")
    private String email;
    @NotBlank(message = "Password cannot be empty")
    @Size(min=8,message = "Password must be atleast 8 characters long")
    // @Pattern(regexp ="[ A-Za-z0-9_@./#&+-]",message=" ")
    private String password;
    @NotBlank(message = "bio cannot be empty")
    private String about;
}
