package com.ayushsingh.bloggingapplication.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ayushsingh.bloggingapplication.payloads.ApiResponse;
import com.ayushsingh.bloggingapplication.payloads.UserDto;
import com.ayushsingh.bloggingapplication.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
    
    @Autowired
    private UserService userService;

    //POST- create user
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid /*Enable validation*/ @RequestBody UserDto userDto){
        UserDto createdUserDto=this.userService.createUser(userDto);
        return new ResponseEntity<UserDto>(createdUserDto,HttpStatus.CREATED);
    }
    //PUT- update user
    @PutMapping("/{userid}") //userid is a path uri variable, we will use it fetch data
    //@PathVariable("userid") if the variable name is same as mapping, we need not specify like this
    public ResponseEntity<UserDto> updateUser(/*Enable Validation*/ @Valid @RequestBody UserDto userDto,@PathVariable("userid") Integer uid){
        UserDto updatedUser=this.userService.updateUser(userDto, uid);
        return ResponseEntity.ok(updatedUser);

    }
    //DELETE- delete user
    // @DeleteMapping("/{userid}")
    //     public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer uid){
    //         // ResponseEntity<?> deletedUser=
    //         this.userService.deleteUser(uid);
    //         return  ResponseEntity.ok(Map.of("message","user deleted successfully"));
    //         // OR
    //          return ResponseEntity(Map.of("message","user deleted successfully"),HttpStatus.OK);

    //     }
        //OR
       
            //ADMIN ROLE CAN USE THIS API
        @DeleteMapping("/{userid}")
        @PreAuthorize("hasRole('ADMIN')") //NOW ONLY THE USERS WITH ADMIN ROLES WILL BE PERMITTED
        public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userid") Integer uid){
            
            // ResponseEntity<?> deletedUser=
            this.userService.deleteUser(uid);
            return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true),HttpStatus.OK);

        }
    //GET- user get
    @GetMapping("/")
    public ResponseEntity<List<UserDto>> getAllusers(){
        return ResponseEntity.ok(this.userService.getAllUsers());
    }
    @GetMapping("/{userid}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("userid") Integer uid){
       
        return ResponseEntity.ok(this.userService.getUserById(uid));
    }
    
}
