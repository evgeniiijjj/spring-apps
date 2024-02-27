package com.example.newsservice.services;

import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.dtos.UserDtoForCreate;
import com.example.newsservice.dtos.UserDtoForDelete;
import com.example.newsservice.dtos.UserRoleDto;
import com.example.newsservice.entities.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserDto> getAll(Pageable page);
    UserDto create(UserDtoForCreate user);
    UserDto update(UserDto user);
    User getByEmail(String email);
    boolean addUserRole(UserRoleDto user);
    boolean existsById(long id);
    void delete(UserDtoForDelete user);
    boolean existsByEmail(String email);
}
