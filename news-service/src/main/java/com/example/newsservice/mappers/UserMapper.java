package com.example.newsservice.mappers;

import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.dtos.UserDtoForCreate;
import com.example.newsservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "newsList", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserDto userDto);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "newsList", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntityForCreate(UserDtoForCreate userDto);
    List<UserDto> toDtoList(List<User> userList);
}
