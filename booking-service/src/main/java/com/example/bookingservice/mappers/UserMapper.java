package com.example.bookingservice.mappers;

import com.example.bookingservice.dtos.UserDto;
import com.example.bookingservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    @Mapping(target = "id", ignore = true)
    User toEntity(UserDto user);
    List<UserDto> toDtoList(List<User> rooms);
}
