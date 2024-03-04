package com.example.newsservice.aop;

import com.example.newsservice.dtos.CommentDto;
import com.example.newsservice.dtos.NewsDto;
import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.dtos.UserDtoForDelete;
import com.example.newsservice.entities.Role;
import com.example.newsservice.entities.enums.RoleType;
import com.example.newsservice.exceptions.AccessDeniedExceptions;
import com.example.newsservice.security.UserDetailsServiceImpl;
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
public class VerifyNewsServiceAspect {

    private final UserDetailsServiceImpl detailsService;

    @Before("@annotation(VerifyUserUpdate)")
    public void verifyUserUpdate(JoinPoint joinPoint) {
        UserDto user = (UserDto) joinPoint.getArgs()[0];
        if (!user.getEmail().equals(detailsService.getUserDetails().getUsername())) {
            throw new AccessDeniedExceptions(
                    MessageFormat
                            .format(
                                    "Пользователь {0} не имеет прав на обновление другого аккаунта!",
                                    user.getEmail()
                            )
            );
        }
    }

    @Before("@annotation(VerifyUserDelete)")
    public void verifyUserDelete(JoinPoint joinPoint) {
        UserDtoForDelete user = (UserDtoForDelete) joinPoint.getArgs()[0];
        if (!user.getEmail().equals(detailsService.getUserDetails().getUsername())) {
            throw new AccessDeniedExceptions(
                    MessageFormat
                            .format(
                                    "Пользователь {0} не имеет прав на удаление другого аккаунта!",
                                    user.getEmail()
                            )
            );
        }
    }

    @Before("@annotation(VerifyNewsUpdate)")
    public void verifyNewsUpdate(JoinPoint joinPoint) {
        NewsService service = (NewsService) joinPoint.getThis();
        NewsDto news = (NewsDto) joinPoint.getArgs()[0];
        Long id = news.getId();
        if (id == null) {
            return;
        }
        Optional<NewsDto> optional = service.findById(id);
        if (optional.isEmpty()) {
            return;
        }
        if (!optional.get().getUser().getEmail().equals(detailsService.getUserDetails().getUsername())) {
            throw new AccessDeniedExceptions(
                    MessageFormat
                            .format(
                                    "Пользователь {0} не имеет прав на редактирование данной новости!",
                                    optional.get().getUser().getEmail()
                            )
            );
        }
    }

    @Before("@annotation(VerifyNewsDelete)")
    public void verifyNewsDelete(JoinPoint joinPoint) {
        if (detailsService
                .getUserDetails().getRoles().stream()
                .map(Role::getAuthority)
                .anyMatch(roleType -> roleType.equals(RoleType.ROLE_ADMIN) || roleType.equals(RoleType.ROLE_MODERATOR))) {
            return;
        }
        NewsService service = (NewsService) joinPoint.getThis();
        Long id = (Long) joinPoint.getArgs()[0];
        if (id == null) {
            return;
        }
        Optional<NewsDto> optional = service.findById(id);
        if (optional.isEmpty()) {
            return;
        }
        if (!optional.get().getUser().getEmail().equals(detailsService.getUserDetails().getUsername())) {
            throw new AccessDeniedExceptions(
                    MessageFormat
                            .format(
                                    "Пользователь {0} не имеет прав на удаление данной новости!",
                                    optional.get().getUser().getEmail()
                            )
            );
        }
    }

    @Before("@annotation(VerifyCommentUpdate)")
    public void verifyCommentUpdate(JoinPoint joinPoint) {
        CommentService service = (CommentService) joinPoint.getThis();
        CommentDto comment = (CommentDto) joinPoint.getArgs()[0];
        Long id = comment.getId();
        if (id == null) {
            return;
        }
        Optional<CommentDto> optional = service.findById(id);
        if (optional.isEmpty()) {
            return;
        }
        if (!optional.get().getUser().getEmail().equals(detailsService.getUserDetails().getUsername())) {
            throw new AccessDeniedExceptions(
                    MessageFormat
                            .format(
                                    "Пользователь {0} не имеет прав на редактирование данного комментария!",
                                    optional.get().getUser().getEmail()
                            )
            );
        }
    }

    @Before("@annotation(VerifyCommentDelete)")
    public void verifyCommentDelete(JoinPoint joinPoint) {
        if (detailsService
                .getUserDetails().getRoles().stream()
                .map(Role::getAuthority)
                .anyMatch(roleType -> roleType.equals(RoleType.ROLE_ADMIN) || roleType.equals(RoleType.ROLE_MODERATOR))) {
            return;
        }
        CommentService service = (CommentService) joinPoint.getThis();
        Long id = (Long) joinPoint.getArgs()[0];
        if (id == null) {
            return;
        }
        Optional<CommentDto> optional = service.findById(id);
        if (optional.isEmpty()) {
            return;
        }
        if (!optional.get().getUser().getEmail().equals(detailsService.getUserDetails().getUsername())) {
            throw new AccessDeniedExceptions(
                    MessageFormat
                            .format(
                                    "Пользователь {0} не имеет прав на удаление данного комментария!",
                                    optional.get().getUser().getEmail()
                            )
            );
        }
    }
}
