package com.example.bookingservice.services;

import com.example.bookingservice.dtos.UserDto;
import com.example.bookingservice.dtos.UserDtoForCreate;
import com.example.bookingservice.entities.User;

public interface UserService {
    UserDto getByName(String name);
    UserDto create(UserDtoForCreate user);
    UserDto update(UserDto user);
    String deleteByName(String name);
    User getUserByName(String name);
}
