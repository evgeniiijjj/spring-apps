package com.example.newsservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "Имя пользователя должно быть указано!")
    private String firstName;
    @NotBlank(message = "Фамилия пользователя должно быть указано!")
    private String lastName;
    @NotBlank(message = "Электронная почта пользователя должна быть указана!")
    private String email;
}