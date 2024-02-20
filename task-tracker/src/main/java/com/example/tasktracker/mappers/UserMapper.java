package com.example.tasktracker.mappers;

import com.example.tasktracker.dtos.UserDto;
import com.example.tasktracker.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto user);
    List<UserDto> toListDto(List<User> users);
}
