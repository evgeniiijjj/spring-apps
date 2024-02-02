package com.example.newsservice.services;

import com.example.newsservice.dtos.CommentDto;
import com.example.newsservice.dtos.UserDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<CommentDto> getAllByNewsIdOrUserId(Long newsId, Long userId);
    CommentDto createOrUpdate(CommentDto commentDto);
    Optional<CommentDto> findById(long id);
    boolean deleteById(UserDto user, long id);
}
