package com.example.newsservice.controllers;

import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.exceptions.DeleteByIdException;
import com.example.newsservice.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService service;

    @GetMapping("api/users")
    private ResponseEntity<List<UserDto>> getAllUsers(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(service.getAll(PageRequest.of(pageNumber, pageSize)));
    }

    @PostMapping("api/users/user")
    private ResponseEntity<UserDto> createOrUpdateUser(@RequestBody @Valid UserDto user) {
        return ResponseEntity.ok(service.createOrUpdate(user));
    }

    @DeleteMapping("api/users/user/{id}")
    private ResponseEntity<Object> deleteUser(@PathVariable long id) {
        if (service.deleteById(id)) {
            return ResponseEntity.ok(MessageFormat.format("id: {0}", id));
        }
        throw new DeleteByIdException(MessageFormat.format("Пользователя с id: {0} не существует!", id));
    }
}
