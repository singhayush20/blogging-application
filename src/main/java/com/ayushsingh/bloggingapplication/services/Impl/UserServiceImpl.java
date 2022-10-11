package com.ayushsingh.bloggingapplication.services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ayushsingh.bloggingapplication.entities.User;
import com.ayushsingh.bloggingapplication.exceptions.ResourceNotFoundException;
import com.ayushsingh.bloggingapplication.payloads.UserDto;
import com.ayushsingh.bloggingapplication.repositories.UserRep;
import com.ayushsingh.bloggingapplication.services.UserService;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRep userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);

        // save the user
        User savedUser = this.userRepo.save(user);
        return this.usertoDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        
        Optional<User> result=userRepo.findById(userId);
        User user=null;
        if(result.isPresent()){
            //get the user
            user=result.get();
        }
        else{
            //throw an exception if the user is not found
            throw new ResourceNotFoundException("User","user id",userId);
        }

        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setEmail(userDto.getEmail());
        user.setPassword(user.getPassword());

        //save the updated user
        User updatedUser=this.userRepo.save(user);
        return this.usertoDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        Optional<User> result=userRepo.findById(userId);
        User user=null;
        if(result.isPresent()){
            //get the user
            user=result.get();
        }
        else{
            //throw an exception if the user is not found
            throw new ResourceNotFoundException("User","user id",userId);
        }
        return this.usertoDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        //get all the users
        List<User> users=this.userRepo.findAll();
        //convert all the users to user dtos
       List<UserDto> userDtos= users.stream().map(user->this.usertoDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        Optional<User> result=userRepo.findById(userId);
        User user=null;
        if(result.isPresent()){
            //get the user
            user=result.get();
        }
        else{
            //throw an exception if the user is not found
            throw new ResourceNotFoundException("User","user id",userId);
        }
        this.userRepo.delete(user);

    }

    private User dtoToUser(UserDto userDto) {
        // User user = new User();
        // user.setId(userDto.getId());
        // user.setAbout(userDto.getAbout());
        // user.setEmail(userDto.getEmail());
        // user.setPassword(userDto.getPassword());
        // user.setName(userDto.getName());
    
        User user=this.modelMapper.map(userDto,User.class);
        return user;

    }

    public UserDto usertoDto(User user) {
        // UserDto userDto = new UserDto();
        // userDto.setId(user.getId());
        // userDto.setName(user.getName());
        // userDto.setEmail(user.getEmail());
        // userDto.setAbout(user.getAbout());
        // userDto.setPassword(user.getPassword());
        
        UserDto userDto=this.modelMapper.map(user,UserDto.class);
        return userDto;
    }

}
