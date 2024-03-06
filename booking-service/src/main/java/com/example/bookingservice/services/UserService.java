package com.example.bookingservice.services;

import com.example.bookingservice.dtos.UserDto;

public interface UserService {
    UserDto getByName(String name);
    UserDto create(UserDto user);
    UserDto update(UserDto user);
    String deleteByName(String name);
}
