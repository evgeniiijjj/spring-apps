package com.example.newsservice.mappers;

import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    @Mapping(target = "newsList", ignore = true)
    @Mapping(target = "comments", ignore = true)
    User toEntity(UserDto userDto);
    List<UserDto> toDtoList(List<User> userList);
}
