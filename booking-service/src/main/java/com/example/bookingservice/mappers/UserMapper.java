package com.example.bookingservice.mappers;

import com.example.bookingservice.dtos.UserDto;
import com.example.bookingservice.dtos.UserDtoForCreate;
import com.example.bookingservice.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    User toEntity(UserDto user);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bookings", ignore = true)
    User toEntityForCreate(UserDtoForCreate user);
    List<UserDto> toDtoList(List<User> users);
}
