package com.example.bookservice.services;

import com.example.bookservice.dtos.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDto> getBooksByCategory(String category);
    BookDto getBookByTitleAndAuthor(String title, String author);
    BookDto createOrUpdateBook(BookDto book);
    Integer deleteById(Integer id);
}