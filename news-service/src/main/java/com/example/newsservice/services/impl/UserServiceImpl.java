package com.example.newsservice.services.impl;

import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.mappers.UserMapper;
import com.example.newsservice.repositories.UserRepository;
import com.example.newsservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Override
    public List<UserDto> getAll(Pageable page) {
        return mapper.toDtoList(repository.findAll(page).toList());
    }

    @Override public boolean existsById(long id) {
        return repository.existsById(id);
    }

    @Override
    public UserDto createOrUpdate(UserDto userDto) {
        return mapper.toDto(repository.save(mapper.toEntity(userDto)));
    }

    @Override
    public boolean deleteById(long id) {
        boolean exists = repository.existsById(id);
        if (exists) {
            repository.deleteById(id);
        }
        return exists;
    }
}
