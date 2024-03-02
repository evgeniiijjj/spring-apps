package com.example.tasktracker.mappers;

import com.example.tasktracker.dtos.UserDto;
import com.example.tasktracker.dtos.UserDtoForCreate;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.enums.RoleType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserDto user);
    @Mapping(target = "roles", ignore = true)
    User toEntityForCreate(UserDtoForCreate user);
    List<UserDto> toListDto(List<User> users);
}
