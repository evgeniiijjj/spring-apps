package com.example.newsservice.services.impl;

import com.example.newsservice.aop.Verifiable;
import com.example.newsservice.dtos.CommentDto;
import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.mappers.CommentMapper;
import com.example.newsservice.repositories.CommentRepository;
import com.example.newsservice.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;
    private final CommentMapper mapper;

    @Override
    public List<CommentDto> getAllByNewsIdOrUserId(Long newsId, Long userId) {
        if (newsId != 0) {
            return mapper.toDtoList(repository.findByNewsId(newsId));
        }
        return mapper.toDtoList(repository.findByUserId(userId));
    }

    @Verifiable
    @Override
    public CommentDto createOrUpdate(CommentDto commentDto) {
        return mapper.toDto(repository.save(mapper.toEntity(commentDto)));
    }

    @Override
    public Optional<CommentDto> findById(long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    @Verifiable
    @Override
    public boolean deleteById(UserDto user, long id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
        }
        return exists;
    }
}
