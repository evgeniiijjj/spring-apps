package com.example.newsservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    @NotBlank(message = "Имя категории должно быть указано!")
    private String name;
}