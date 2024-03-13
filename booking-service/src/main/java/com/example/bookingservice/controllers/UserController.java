package com.example.bookingservice.controllers;

import com.example.bookingservice.dtos.AllElementsResult;
import com.example.bookingservice.dtos.UserDto;
import com.example.bookingservice.dtos.UserDtoForCreate;
import com.example.bookingservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService service;

    @GetMapping("/api/users")
    public ResponseEntity<AllElementsResult<UserDto>> getUsers(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        return ResponseEntity.ok(service.getAll(PageRequest.of(pageNumber, pageSize)));
    }

    @GetMapping("/api/users/user/{name}")
    public ResponseEntity<UserDto> getUserByName(@PathVariable String name) {
        return ResponseEntity.ok(service.getByName(name));
    }

    @PostMapping("/api/users/user")
    public ResponseEntity<UserDto> create(@RequestBody UserDtoForCreate user) {
        UserDto result = service.create(user);
        return ResponseEntity.created(URI.create("/api/users/user/" + result.getUserName())).body(result);
    }

    @PutMapping("/api/users/user")
    public ResponseEntity<UserDto> update(@RequestBody UserDto user) {
        return ResponseEntity.accepted().body(service.update(user));
    }

    @DeleteMapping("/api/users/user/{name}")
    public ResponseEntity<String> delete(@PathVariable String name) {
        return ResponseEntity.ok(service.deleteByName(name));
    }
}