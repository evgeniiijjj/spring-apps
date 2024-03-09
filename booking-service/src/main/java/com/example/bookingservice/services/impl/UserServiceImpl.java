package com.example.bookingservice.services.impl;

import com.example.bookingservice.aop.VerifyUserGetOrDelete;
import com.example.bookingservice.aop.VerifyUserUpdate;
import com.example.bookingservice.dtos.UserDto;
import com.example.bookingservice.dtos.UserDtoForCreate;
import com.example.bookingservice.entities.User;
import com.example.bookingservice.exceptions.NotFoundException;
import com.example.bookingservice.exceptions.UserAlreadyExistsException;
import com.example.bookingservice.mappers.UserMapper;
import com.example.bookingservice.repositories.UserRepository;
import com.example.bookingservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @VerifyUserGetOrDelete
    @Override
    public UserDto getByName(String name) {
        return mapper.toDto(
                getUserByName(name)
        );
    }

    @Override
    public User getUserByName(String name) {
        return repository.findByUserName(name)
                .orElseThrow(() ->
                        new NotFoundException(
                                MessageFormat.format("User with name - {0} not found!", name)
                        )
                );
    }

    @Override
    public UserDto create(UserDtoForCreate userDto) {
        validateUserNameAndEmail(userDto.getUserName(), userDto.getEmail());
        User user = mapper.toEntityForCreate(userDto);
        user.setPassword(encoder.encode(user.getPassword()));
        return mapper.toDto(repository.save(user));
    }

    @VerifyUserUpdate
    @Override
    public UserDto update(UserDto userDto) {
        validateUserName(userDto.getUserName());
        User user = mapper.toEntity(userDto);
        user.setId(repository.findByUserName(user.getUserName()).orElse(user).getId());
        return mapper.toDto(repository.save(user));
    }

    @VerifyUserGetOrDelete
    @Override
    public String deleteByName(String name) {
        validateUserName(name);
        repository.deleteByUserName(name);
        return name;
    }

    private void validateUserName(String userName) {
        if (!repository.existsByUserName(userName)) {
            throw new NotFoundException(MessageFormat.format("User with name - {0} not found!", userName));
        }
    }

    private void validateUserNameAndEmail(String userName, String email) {

        if (repository.existsByUserName(userName)) {
            throw new UserAlreadyExistsException(MessageFormat.format("User with name - {0} already exists!", userName));
        }

        if (repository.existsByEmail(email)) {
            throw new UserAlreadyExistsException(MessageFormat.format("User with email - {0} already exists!", email));
        }
    }
}
