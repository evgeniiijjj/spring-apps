package com.example.newsservice.services;

import com.example.newsservice.dtos.NewsDto;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NewsService {
    List<NewsDto> getAll(Pageable page, Long userId, Long categoryId);
    NewsDto createOrUpdate(NewsDto newsDto);
    Optional<NewsDto> findById(long id);
    boolean existsById(long id);
    boolean deleteById(long id);
}
