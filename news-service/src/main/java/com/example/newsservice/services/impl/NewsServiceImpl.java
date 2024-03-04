package com.example.newsservice.services.impl;

import com.example.newsservice.aop.VerifyNewsDelete;
import com.example.newsservice.aop.VerifyNewsUpdate;
import com.example.newsservice.dtos.NewsDto;
import com.example.newsservice.entities.News;
import com.example.newsservice.entities.User;
import com.example.newsservice.exceptions.NotFoundException;
import com.example.newsservice.mappers.NewsMapper;
import com.example.newsservice.repositories.NewsRepository;
import com.example.newsservice.repositories.UserRepository;
import com.example.newsservice.services.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    public final NewsMapper mapper;

    @Override
    public List<NewsDto> getAll(Pageable page, String userEmail, Long categoryId) {

        long userId = userRepository.findByEmail(userEmail).map(User::getId).orElse(0L);

        if (userId != 0 && categoryId != 0) {
            return mapper.toDtoList(newsRepository.findByUserIdAndCategoryId(page, userId, categoryId));
        }
        if (userId != 0) {
            return mapper.toDtoList(newsRepository.findByUserId(userId));
        }
        if (categoryId != 0) {
            return mapper.toDtoList(newsRepository.findByCategoryId(categoryId));
        }
        return mapper.toDtoList(newsRepository.findAll());
    }

    @VerifyNewsUpdate
    @Override
    public NewsDto createOrUpdate(NewsDto newsDto) {
        News news = mapper.toEntity(newsDto);
        news.setUser(
                userRepository.findByEmail(news.getUser().getEmail())
                        .orElseThrow(() -> new NotFoundException(MessageFormat.format("Пользователь {0} не найден!", news.getUser().getEmail())))
        );
        return mapper.toDto(newsRepository.save(news));
    }

    @Override
    public boolean existsById(long id) {
        return newsRepository.existsById(id);
    }

    public Optional<NewsDto> findById(long id) {
        return newsRepository.findById(id).map(mapper::toDto);
    }

    @VerifyNewsDelete
    @Override
    public void deleteById(long id) {
        if (!newsRepository.existsById(id)) {
            throw new NotFoundException(
                    MessageFormat.format("Новость с id {0} не найдена!", id)
            );
        }
        newsRepository.deleteById(id);
    }
}
