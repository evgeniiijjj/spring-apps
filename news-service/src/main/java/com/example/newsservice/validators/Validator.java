package com.example.newsservice.validators;

import com.example.newsservice.dtos.CategoryDto;
import com.example.newsservice.dtos.NewsDto;
import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.services.CategoryService;
import com.example.newsservice.services.NewsService;
import com.example.newsservice.services.UserService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Validator implements ConstraintValidator<Exists, Object> {

    private final CategoryService categoryService;
    private final NewsService newsService;
    private final UserService userService;

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        String className = value.getClass().getName();
        if (className.endsWith("NewsDto")) {
            NewsDto news = (NewsDto) value;
            Long id = news.getId();
            return id != null && newsService.existsById(id);
        }
        if (className.endsWith("UserDto")) {
            UserDto user = (UserDto) value;
            Long id = user.getId();
            return id != null && userService.existsById(id);
        }
        if (className.endsWith("CategoryDto")) {
            CategoryDto category = (CategoryDto) value;
            Long id = category.getId();
            return id != null && categoryService.existsById(id);
        }
        return false;
    }
}
