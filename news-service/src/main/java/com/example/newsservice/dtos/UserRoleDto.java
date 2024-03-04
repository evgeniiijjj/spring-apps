package com.example.newsservice.dtos;

import com.example.newsservice.entities.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserRoleDto {
    private String email;
    private RoleType role;
}
