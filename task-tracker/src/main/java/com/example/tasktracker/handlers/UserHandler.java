package com.example.tasktracker.handlers;

import com.example.tasktracker.dtos.UserDto;
import com.example.tasktracker.mappers.UserMapper;
import com.example.tasktracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@Component
@Slf4j
public class UserHandler {

    private final UserRepository repository;
    private final UserMapper mapper;

    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
        log.info("Get all users");
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(repository.findAll().map(mapper::toDto), UserDto.class);
    }

    public Mono<ServerResponse> getUserById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return repository
                .findById(id)
                .map(mapper::toDto)
                .flatMap(userDto -> {
                    log.info("Get user by id - {}", id);
                    return ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(Mono.just(userDto), UserDto.class);

                })
                .switchIfEmpty(
                        ServerResponse
                                .notFound()
                                .build()
                );
    }

    public Mono<ServerResponse> createUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserDto.class)
                .flatMap(user -> {
                    log.info("User for create {}", user);
                    return repository.save(mapper.toEntity(user)).map(mapper::toDto);
                })
                .flatMap(user -> ServerResponse.created(URI.create("api/users/user/" + user.getId())).body(Mono.just(user), UserDto.class));
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserDto.class)
                .flatMap(user -> repository.existsById(user.getId())
                                .flatMap(exists -> {
                                    if (exists) {
                                        log.info("User for update {}", user);
                                        return ServerResponse.accepted().body(Mono.just(user), UserDto.class);
                                    }
                                    log.info("User with id - {} not found", user.getId());
                                    return ServerResponse.notFound().build();
                                })
                );
    }

    public Mono<ServerResponse> deleteUserById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return repository
                .existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        log.info("Delete user with id - {}", id);
                        return ServerResponse
                                .noContent()
                                .build(repository.deleteById(id));
                    }
                    log.info("User with id - {} is not found", id);
                    return ServerResponse
                            .notFound()
                            .build();
                });
    }
}
