package com.example.newsservice.services;

import com.example.newsservice.dtos.CommentDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<CommentDto> getAllByNewsIdOrUserId(Long newsId, String userEmail);
    CommentDto createOrUpdate(CommentDto commentDto);
    Optional<CommentDto> findById(long id);
    void deleteById(long id);
}
