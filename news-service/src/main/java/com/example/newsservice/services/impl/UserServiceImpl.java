package com.example.newsservice.services.impl;

import com.example.newsservice.aop.VerifyUserDelete;
import com.example.newsservice.aop.VerifyUserUpdate;
import com.example.newsservice.dtos.UserDto;
import com.example.newsservice.dtos.UserDtoForCreate;
import com.example.newsservice.dtos.UserDtoForDelete;
import com.example.newsservice.dtos.UserRoleDto;
import com.example.newsservice.entities.Role;
import com.example.newsservice.entities.User;
import com.example.newsservice.entities.enums.RoleType;
import com.example.newsservice.exceptions.NotFoundException;
import com.example.newsservice.mappers.UserMapper;
import com.example.newsservice.repositories.UserRepository;
import com.example.newsservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;

    @Override
    public User getByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(MessageFormat.format("Пользователь с email {0} не найден!", email)));
    }

    @Override
    public List<UserDto> getAll(Pageable page) {
        return mapper.toDtoList(repository.findAll(page).toList());
    }

    @Override
    public boolean existsById(long id) {
        return repository.existsById(id);
    }

    @Override
    public UserDto create(UserDtoForCreate userDto) {
        User user = mapper.toEntityForCreate(userDto);
        user.setPassword(encoder.encode(userDto.getPassword()));
        user.setRoles(Collections.singletonList(Role.from(RoleType.ROLE_USER)));
        return mapper.toDto(repository.save(user));
    }

    @VerifyUserUpdate
    @Override
    public UserDto update(UserDto userDto) {
        User user = mapper.toEntity(userDto);
        user.setId(repository.findByEmail(userDto.getEmail()).map(User::getId).orElse(0L));
        return mapper.toDto(repository.save(user));
    }

    @Override
    public boolean addUserRole(UserRoleDto userRole) {
        return repository.findByEmail(userRole.getEmail())
                .map(user -> {
                    user.addRole(userRole.getRole());
                    return true;
                })
                .orElse(false);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @VerifyUserDelete
    @Override
    public void delete(UserDtoForDelete userForDelete) {
        repository.findByEmail(userForDelete.getEmail())
                .map(user -> {
                    repository.deleteById(user.getId());
                    return true;
                })
                .orElseThrow(() -> new NotFoundException(
                        MessageFormat.format("Пользователь с email {0} не найден!", userForDelete.getEmail()))
                );
    }
}
