package com.example.newsservice.mappers;

import com.example.newsservice.dtos.CategoryDto;
import com.example.newsservice.entities.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    @Mapping(target = "newsList", ignore = true)
    Category toEntity(CategoryDto categoryDto);
    List<CategoryDto> toDtoList(List<Category> categoryList);
}
