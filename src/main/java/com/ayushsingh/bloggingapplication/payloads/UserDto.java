package com.ayushsingh.bloggingapplication.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
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
    private String username;

    @Email(message = "Email is not Valid!")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min=8,message = "Password must be atleast 8 characters long")   
    private String password;

    @NotBlank(message = "bio cannot be empty")
    private String about;

    private Set<RoleDto> roles = new HashSet<>();

    @NotBlank(message = "first name cannot be blank")
    @Size(max = 50, message="first name cannot be more than 50 characters")
    private String firstName;

    @Size(max=50, message="last name cannot be more than 50 characters")
    private String lastName;

}
