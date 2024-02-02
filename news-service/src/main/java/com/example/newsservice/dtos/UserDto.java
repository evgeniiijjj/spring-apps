package com.example.newsservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;
    @NotBlank(message = "Имя пользователя должно быть указано!")
    private String firstName;
    @NotBlank(message = "Фамилия пользователя должно быть указано!")
    private String lastName;
    @NotBlank(message = "Электронная почта пользователя должна быть указана!")
    private String email;
}