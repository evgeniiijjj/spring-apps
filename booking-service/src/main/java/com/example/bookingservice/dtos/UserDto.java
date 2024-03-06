package com.example.bookingservice.dtos;

import com.example.bookingservice.enums.RoleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private String userName;
    private String password;
    private String email;
    private RoleType role;
}
