package com.example.newsservice.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoForCreate {
    @NotBlank(message = "Имя пользователя должно быть указано!")
    private String firstName;
    @NotBlank(message = "Фамилия пользователя должно быть указано!")
    private String lastName;
    @NotBlank(message = "Электронная почта пользователя должна быть указана!")
    private String email;
    @NotBlank(message = "Пароль пользователя должен быть указан!")
    private String password;

    @JsonIgnore
    public UserDto getUserDto() {
        return new UserDto(firstName, lastName, email);
    }
}
