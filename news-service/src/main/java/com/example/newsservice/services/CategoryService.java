package com.example.newsservice.services;

import com.example.newsservice.dtos.CategoryDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> getAll(Pageable page);
    CategoryDto getById(long id);
    CategoryDto createOrUpdate(CategoryDto categoryDto);
    boolean existsById(long id);
    void deleteById(long id);
}
