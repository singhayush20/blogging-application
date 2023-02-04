package com.ayushsingh.bloggingapplication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.bloggingapplication.constants.AppConstants;
import com.ayushsingh.bloggingapplication.payloads.SuccessResponse;
import com.ayushsingh.bloggingapplication.payloads.UserDto;
import com.ayushsingh.bloggingapplication.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/blog/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST- create user
    @PostMapping("/new-user")
    public ResponseEntity<SuccessResponse<UserDto>> createUser(
            @Valid /* Enable validation */ @RequestBody UserDto userDto) {
        UserDto createdUserDto = this.userService.createUser(userDto);
        SuccessResponse<UserDto> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, createdUserDto);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    // PUT- update user
    @PutMapping("/update-user") // userid is a path uri variable, we will use it fetch data
    // @PathVariable("userid") if the variable name is same as mapping, we need not
    // specify like this
    public ResponseEntity<SuccessResponse<String>> updateUser(
            /* Enable Validation */ @Valid @RequestBody UserDto userDto,
            @RequestParam(name = "userid") Integer uid) {
        this.userService.updateUser(userDto, uid);
        SuccessResponse<String> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, "Profile updated successfully");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);

    }

    // ADMIN ROLE CAN USE THIS API
    // @PreAuthorize("hasRole('ROLE_ADMIN')") // NOW ONLY THE USERS WITH ADMIN ROLES
    // WILL BE PERMITTED
    @DeleteMapping("/delete-user")
    public ResponseEntity<SuccessResponse<String>> deleteUser(@RequestParam(name = "userid") Integer uid) {

        // ResponseEntity<?> deletedUser=
        this.userService.deleteUser(uid);
        SuccessResponse<String> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, "User deleted successfully");
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    // GET- user get
    @GetMapping("/get-all-users")
    public ResponseEntity<SuccessResponse<List<UserDto>>> getAllusers() {
        SuccessResponse<List<UserDto>> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, this.userService.getAllUsers());
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/get-user-by-email")
    public ResponseEntity<SuccessResponse<UserDto>> getUserByEmail(@RequestParam(name = "email") String email) {
        SuccessResponse<UserDto> successResponse = new SuccessResponse<>(AppConstants.SUCCESS_CODE,
                AppConstants.SUCCESS_MESSAGE, this.userService.getUserByEmail(email));
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

}
