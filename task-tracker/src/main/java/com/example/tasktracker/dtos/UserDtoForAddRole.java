package com.example.tasktracker.dtos;

import com.example.tasktracker.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoForAddRole {
    private String email;
    private RoleType role;
}
