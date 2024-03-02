package com.example.tasktracker.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDtoForCreate {
    @NotBlank(message = "User name must be specified!")
    private String userName;
    @NotBlank(message = "User email must be specified!")
    private String email;
    @NotBlank(message = "User password must be specified!")
    private String password;
}
