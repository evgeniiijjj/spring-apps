package com.example.bookservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto implements Serializable {
    private Integer id;
    @NotBlank(message = "Название книги не должно быть пустым!")
    private String title;
    @NotBlank(message = "Поле автор не должно быть пустым!")
    private String author;
    @NotBlank(message = "Поле категория не должно быть пустым!")
    private String category;
}
