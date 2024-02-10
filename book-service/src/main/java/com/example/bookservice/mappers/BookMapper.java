package com.example.bookservice.mappers;

import com.example.bookservice.dtos.BookDto;
import com.example.bookservice.entities.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "category", source = "category.title")
    BookDto toDto(Book book);
    @Mapping(target = "category", ignore = true)
    Book toEntity(BookDto book);
    List<BookDto> toDtoList(List<Book> books);
}
