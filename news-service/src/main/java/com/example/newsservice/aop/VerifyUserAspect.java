package com.example.newsservice.aop;

import com.example.newsservice.dtos.CommentDto;
import com.example.newsservice.dtos.NewsDto;
import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.dtos.UserDtoForDelete;
import com.example.newsservice.entities.Role;
import com.example.newsservice.entities.enums.RoleType;
import com.example.newsservice.exceptions.AccessDeniedExceptions;
import com.example.newsservice.repositories.UserRepository;
import com.example.newsservice.security.UserDetailsServiceImpl;
import com.example.newsservice.services.CommentService;
import com.example.newsservice.services.NewsService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class VerifyUserAspect {

    private final UserDetailsServiceImpl detailsService;

    @Before("@annotation(Verifiable)")
    public void verify(JoinPoint joinPoint) {
        Object service = joinPoint.getThis();
        Object[] args = joinPoint.getArgs();
        if (args.length < 1) {
            return;
        }
        String username = detailsService.getUserDetails().getUsername();
        String message = "";
        if (args[0] instanceof UserDtoForDelete user) {
            if (user.getEmail().equals(username)) {
                return;
            }
            message = MessageFormat.format("Пользователь {0} не имеет прав на удаление другого аккаунта!", username);
        }
        if (args[0] instanceof UserDto userDto) {
            if (userDto.getEmail().equals(username)) {
                return;
            }
            message = MessageFormat.format("Пользователь {0} не имеет прав на обновление другого аккаунта!", username);
        }
        if (args[0] instanceof NewsDto newsDto) {
            Long id = newsDto.getId();
            if (id == null) {
                return;
            }
            Optional<NewsDto> optional = ((NewsService) service).findById(id);
            if (optional.isEmpty()) {
                return;
            }
            if (optional.get().getUser().getEmail().equals(username)) {
                return;
            }
            message = MessageFormat.format("Пользователь {0} не имеет прав на редактирование данной новости!", username);
        }
        if (args[0] instanceof CommentDto commentDto) {
            Long id = commentDto.getId();
            if (id == null) {
                return;
            }
            Optional<CommentDto> optional = ((CommentService) service).findById(id);
            if (optional.isEmpty()) {
                return;
            }
            if (optional.get().getUser().getEmail().equals(username)) {
                return;
            }
            message = MessageFormat.format("Пользователь {0} не имеет прав на редактирование данного комментария!", username);
        }
        if (args[0] instanceof Long id) {
            boolean checkRole = detailsService
                    .getUserDetails().getRoles().stream()
                    .map(Role::getAuthority)
                    .anyMatch(roleType -> roleType.equals(RoleType.ROLE_ADMIN) || roleType.equals(RoleType.ROLE_MODERATOR));

            if (service instanceof NewsService newsService) {
                Optional<NewsDto> optional = newsService.findById(id);
                if (optional.isPresent() && !optional.get().getUser().getEmail().equals(username) && !checkRole) {
                    message = MessageFormat.format("Пользователь {0} не имеет прав на удаление данной новости!", username);
                }
            }
            if (service instanceof CommentService commentService) {
                Optional<CommentDto> optional = commentService.findById(id);
                if (optional.isPresent() && !optional.get().getUser().getEmail().equals(username) && !checkRole) {
                    message = MessageFormat.format("Пользователь {0} не имеет прав на удаление данного комментария!", username);
                }
            }
        }
        if (!message.isEmpty()) {
            throw new AccessDeniedExceptions(message);
        }
    }
}
