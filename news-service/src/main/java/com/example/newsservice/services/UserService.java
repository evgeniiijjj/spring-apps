package com.example.newsservice.services;

import com.example.newsservice.dtos.UserDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    List<UserDto> getAll(Pageable page);
    UserDto createOrUpdate(UserDto user);
    boolean existsById(long id);
    boolean deleteById(long id);
}
