package com.ayushsingh.bloggingapplication.services;

import java.util.List;

import com.ayushsingh.bloggingapplication.payloads.UserDto;

public interface UserService {
    UserDto registerNewUser(UserDto userDto);

    UserDto createUser(UserDto userDto);

    UserDto updateUser(UserDto userDto, Integer userId);

    UserDto getUserByEmail(String email);

    List<UserDto> getAllUsers();

    void deleteUser(Integer userId);

    void logoutUser(Integer userid, String deviceToken);
}
