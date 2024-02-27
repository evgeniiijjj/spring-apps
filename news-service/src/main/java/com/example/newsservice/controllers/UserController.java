package com.example.newsservice.controllers;

import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.dtos.UserDtoForCreate;
import com.example.newsservice.dtos.UserDtoForDelete;
import com.example.newsservice.dtos.UserRoleDto;
import com.example.newsservice.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService service;

    @GetMapping("/api/users")
    private ResponseEntity<List<UserDto>> getAllUsers(@RequestParam int pageNumber, @RequestParam int pageSize) {
        return ResponseEntity.ok(service.getAll(PageRequest.of(pageNumber, pageSize)));
    }

    @PostMapping("/api/users/user")
    private ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDtoForCreate user) {
        return ResponseEntity.ok(service.create(user));
    }

    @PutMapping("/api/users/user")
    private ResponseEntity<UserDto> updateUser(@RequestBody @Valid UserDto user) {
        return ResponseEntity.ok(service.update(user));
    }

    @PutMapping("/api/users/user/role")
    private ResponseEntity<Void> addUserRole(@RequestBody @Valid UserRoleDto user) {
        if (service.addUserRole(user)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/api/users/user")
    private ResponseEntity<Object> deleteUser(@RequestBody UserDtoForDelete user) {
        service.delete(user);
        return ResponseEntity.ok(MessageFormat.format("Пользователь с email: {0} успешно удален!", user.getEmail()));
    }
}
