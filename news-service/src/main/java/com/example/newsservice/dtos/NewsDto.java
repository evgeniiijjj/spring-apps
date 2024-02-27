package com.example.newsservice.dtos;

import com.example.newsservice.validators.Exists;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDto {
    private Long id;
    @NotBlank(message = "Новость должна содержать не пустой контент!")
    private String content;
    @Exists(message = "Данная категория отсутствует!")
    private CategoryDto category;
    @Exists(message = "Данный пользователь отсутствует!")
    private UserDto user;
    private Integer commentsNum;
}
