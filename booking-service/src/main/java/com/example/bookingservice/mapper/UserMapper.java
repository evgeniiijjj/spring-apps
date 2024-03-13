package com.example.bookingservice.mapper;

import com.example.bookingservice.dto.UserDto;
import com.example.bookingservice.dto.UserDtoForCreate;
import com.example.bookingservice.entity.User;
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
