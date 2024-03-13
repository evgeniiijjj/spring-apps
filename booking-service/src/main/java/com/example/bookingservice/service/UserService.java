package com.example.bookingservice.service;

import com.example.bookingservice.aop.VerifyUserGetOrDelete;
import com.example.bookingservice.aop.VerifyUserUpdate;
import com.example.bookingservice.dto.AllElementsResult;
import com.example.bookingservice.dto.UserDto;
import com.example.bookingservice.dto.UserDtoForCreate;
import com.example.bookingservice.entity.User;
import com.example.bookingservice.event.Event;
import com.example.bookingservice.event.RegistrationEvent;
import com.example.bookingservice.exception.NotFoundException;
import com.example.bookingservice.exception.UserAlreadyExistsException;
import com.example.bookingservice.mapper.UserMapper;
import com.example.bookingservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final PasswordEncoder encoder;

    @Value("${app.kafka.registrationTopic}")
    private String topicName;
    private final KafkaTemplate<String, Event<?>> template;

    public AllElementsResult<UserDto> getAll(Pageable pageable) {
        Page<User> result = repository.findAll(pageable);
        return new AllElementsResult<>(result.getTotalElements(), mapper.toDtoList(result.getContent()));
    }

    @VerifyUserGetOrDelete
    public UserDto getByName(String name) {
        return mapper.toDto(
                getUserByName(name)
        );
    }

    public User getUserByName(String name) {
        return repository.findByUserName(name)
                .orElseThrow(() ->
                        new NotFoundException(
                                MessageFormat.format("User with name - {0} not found!", name)
                        )
                );
    }

    public UserDto create(UserDtoForCreate userDto) {
        validateUserNameAndEmail(userDto.getUserName(), userDto.getEmail());
        User user = mapper.toEntityForCreate(userDto);
        user.setPassword(encoder.encode(user.getPassword()));
        user = repository.save(user);
        template.send(topicName, new RegistrationEvent(user.getId(), user.getUserName(), Instant.now()));
        return mapper.toDto(user);
    }

    @VerifyUserUpdate
    public UserDto update(UserDto userDto) {
        validateUserName(userDto.getUserName());
        User user = mapper.toEntity(userDto);
        user.setId(repository.findByUserName(user.getUserName()).orElse(user).getId());
        return mapper.toDto(repository.save(user));
    }

    @VerifyUserGetOrDelete
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
