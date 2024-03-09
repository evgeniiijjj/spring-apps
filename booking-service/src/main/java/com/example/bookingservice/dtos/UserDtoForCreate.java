package com.example.bookingservice.dtos;

import com.example.bookingservice.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoForCreate {
    private String userName;
    private String password;
    private String email;
    private RoleType role;
}
