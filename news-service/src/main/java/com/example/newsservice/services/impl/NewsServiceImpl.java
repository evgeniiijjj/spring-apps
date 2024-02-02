package com.example.newsservice.services.impl;

import com.example.newsservice.aop.Verifiable;
import com.example.newsservice.dtos.NewsDto;
import com.example.newsservice.entities.News;
import com.example.newsservice.mappers.NewsMapper;
import com.example.newsservice.repositories.NewsRepository;
import com.example.newsservice.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository repository;
    public final NewsMapper mapper;

    @Override
    public List<NewsDto> getAll(Pageable page, Long userId, Long categoryId) {

        if (userId != 0 && categoryId != 0) {
            return mapper.toDtoList(repository.findByUserIdAndCategoryId(page, userId, categoryId));
        }
        if (userId != 0) {
            return mapper.toDtoList(repository.findByUserId(userId));
        }
        if (categoryId != 0) {
            return mapper.toDtoList(repository.findByCategoryId(categoryId));
        }
        return mapper.toDtoList(repository.findAll());
    }

    @Verifiable
    @Override
    public NewsDto createOrUpdate(NewsDto newsDto) {
        return mapper.toDto(repository.save(mapper.toEntity(newsDto)));
    }

    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    public Optional<NewsDto> findById(long id) {
        return repository.findById(id).map(mapper::toDto);
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
