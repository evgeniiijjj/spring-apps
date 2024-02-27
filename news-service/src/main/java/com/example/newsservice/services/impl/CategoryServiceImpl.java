package com.example.newsservice.services.impl;

import com.example.newsservice.dtos.CategoryDto;
import com.example.newsservice.exceptions.NotFoundException;
import com.example.newsservice.mappers.CategoryMapper;
import com.example.newsservice.repositories.CategoryRepository;
import com.example.newsservice.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
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
    public CategoryDto getById(long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        MessageFormat.format("Категория id {0} не найдена!", id)
                ))
        );
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
    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException(
                    MessageFormat.format("Категория с id {0} не найдена!", id)
            );
        }
        repository.deleteById(id);
    }
}
