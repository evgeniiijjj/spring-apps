package com.example.newsservice.dtos;

import com.example.newsservice.validators.Exists;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private Long id;
    @NotBlank(message = "Комментарий не должен быть пустым!")
    private String comment;
    @Exists(message = "Данный пользователь не существует!")
    private UserDto user;
    @Exists(message = "Данная новость не существует!")
    private NewsDto news;
}
