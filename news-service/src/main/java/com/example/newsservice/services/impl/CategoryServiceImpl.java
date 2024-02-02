package com.example.newsservice.services.impl;

import com.example.newsservice.dtos.CategoryDto;
import com.example.newsservice.mappers.CategoryMapper;
import com.example.newsservice.repositories.CategoryRepository;
import com.example.newsservice.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Override
    public List<CategoryDto> getAll(Pageable page) {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public CategoryDto createOrUpdate(CategoryDto categoryDto) {
        return mapper.toDto(repository.save(mapper.toEntity(categoryDto)));
    }

    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    @Override
    public boolean deleteById(long id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
        }
        return exists;
    }
}
