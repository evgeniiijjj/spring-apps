package com.example.newsservice.aop;

import com.example.newsservice.dtos.CommentDto;
import com.example.newsservice.dtos.NewsDto;
import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.exceptions.AccessDeniedExceptions;
import com.example.newsservice.services.CommentService;
import com.example.newsservice.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class VerifyUserAspect {
    @Before("@annotation(Verifiable)")
    public void verify(JoinPoint joinPoint) {
        Object service = joinPoint.getThis();
        Object[] args = joinPoint.getArgs();
        if (args.length < 1) {
            return;
        }
        Long id = null;
        UserDto user = null;
        Optional<UserDto> optional = Optional.empty();
        String message = "";
        if (service instanceof NewsService newsService) {
            NewsDto news = (NewsDto) args[0];
            id = news.getId();
            if (id == null) {
                return;
            }
            user = news.getUser();
            optional = newsService.findById(id).map(NewsDto::getUser);
            message = MessageFormat.format("Пользователь с id: {0} не имеет прав на редактирование данной новости!", id);
        }
        if (service instanceof CommentService commentService) {
            if (args.length < 2) {
                CommentDto comment = (CommentDto) args[0];
                id = comment.getId();
                if (id == null) {
                    return;
                }
                user = comment.getUser();
            } else {
                id = (Long) args[1];
                user = (UserDto) args[0];
            }
            optional = commentService.findById(id).map(CommentDto::getUser);
            message = MessageFormat.format("Пользователь с id: {0} не имеет прав на редактирование данного комментария!", id);
        }
        if (optional.isPresent() && !optional.get().getId().equals(user.getId())) {
            throw new AccessDeniedExceptions(message);
        }
    }
}
