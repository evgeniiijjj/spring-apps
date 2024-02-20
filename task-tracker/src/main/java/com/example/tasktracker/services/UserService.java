package com.example.tasktracker.services;

import com.example.tasktracker.dtos.UserDto;
import com.example.tasktracker.mappers.UserMapper;
import com.example.tasktracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public Flux<UserDto> getAllUsers() {
        return userRepository.findAll().map(userMapper::toDto);
    }

    public Mono<UserDto> getUserById(String id) {
        return userRepository.findById(id).map(userMapper::toDto);
    }

    public Mono<UserDto> createUser(UserDto user) {
        user.setId(UUID.randomUUID().toString());
        return userRepository.save(userMapper.toEntity(user)).map(userMapper::toDto);
    }

    public Mono<UserDto> updateUser(UserDto user) {
        return userRepository.save(userMapper.toEntity(user)).map(userMapper::toDto);
    }

    public Mono<Void> deleteUserById(String id) {
        return userRepository.deleteById(id);
    }
}
