package com.example.newsservice.services.impl;

import com.example.newsservice.aop.Verifiable;
import com.example.newsservice.dtos.CommentDto;
import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.entities.Comment;
import com.example.newsservice.entities.User;
import com.example.newsservice.exceptions.NotFoundException;
import com.example.newsservice.mappers.CommentMapper;
import com.example.newsservice.repositories.CommentRepository;
import com.example.newsservice.repositories.NewsRepository;
import com.example.newsservice.repositories.UserRepository;
import com.example.newsservice.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;
    private final CommentMapper mapper;

    @Override
    public List<CommentDto> getAllByNewsIdOrUserId(Long newsId, String userEmail) {

        long userId = userRepository.findByEmail(userEmail).map(User::getId).orElse(0L);

        if (newsId != 0) {
            return mapper.toDtoList(commentRepository.findByNewsId(newsId));
        }
        return mapper.toDtoList(commentRepository.findByUserId(userId));
    }

    @Verifiable
    @Override
    public CommentDto createOrUpdate(CommentDto commentDto) {
        Comment comment = mapper.toEntity(commentDto);
        comment.setUser(
                userRepository.findByEmail(comment.getUser().getEmail())
                        .orElseThrow(() -> new NotFoundException(MessageFormat.format("Пользователь с email {0} не найден!", comment.getUser().getEmail())))
        );
        comment.setNews(
                newsRepository.findById(commentDto.getNews().getId())
                        .orElseThrow(() -> new NotFoundException(MessageFormat.format("Новость с id {0} не найдена!", comment.getNews().getId())))
        );
        return mapper.toDto(commentRepository.save(comment));
    }

    @Override
    public Optional<CommentDto> findById(long id) {
        return commentRepository.findById(id).map(mapper::toDto);
    }

    @Verifiable
    @Override
    public void deleteById(long id) {
        if (!commentRepository.existsById(id)) {
            throw new NotFoundException(
                    MessageFormat.format("Комментарий с id {0} не найден!", id)
            );
        }
        commentRepository.deleteById(id);
    }
}
