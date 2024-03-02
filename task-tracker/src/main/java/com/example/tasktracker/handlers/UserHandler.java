package com.example.tasktracker.handlers;

import com.example.tasktracker.dtos.UserDto;
import com.example.tasktracker.dtos.UserDtoForAddRole;
import com.example.tasktracker.dtos.UserDtoForCreate;
import com.example.tasktracker.enums.RoleType;
import com.example.tasktracker.exceptions.AccessDeniedException;
import com.example.tasktracker.exceptions.AlreadyExistsUserException;
import com.example.tasktracker.mappers.UserMapper;
import com.example.tasktracker.repositories.UserRepository;
import com.example.tasktracker.security.AppUserPrincipal;
import com.example.tasktracker.security.UserDetailsServiceImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.text.MessageFormat;
import java.util.Set;

@RequiredArgsConstructor
@Validated
@Component
@Slf4j
public class UserHandler {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final UserMapper mapper;
    @Setter
    private UserDetailsServiceImpl detailsService;

    public Mono<ServerResponse> getAllUsers(ServerRequest serverRequest) {
        log.info("Get all users");
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(repository.findAll().map(mapper::toDto), UserDto.class);
    }

    public Mono<ServerResponse> getUserByEmail(ServerRequest serverRequest) {
        String email = serverRequest.pathVariable("email");
        return repository
                .findByEmail(email)
                .map(mapper::toDto)
                .flatMap(userDto -> {
                    log.info("Get user by email - {}", email);
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
        return serverRequest.bodyToMono(UserDtoForCreate.class)
                .flatMap(this::saveUser)
                .flatMap(user -> ServerResponse.created(URI.create("api/users/user/" + user.getEmail())).body(Mono.just(user), UserDto.class))
                .onErrorResume(e -> ServerResponse.badRequest().body(Mono.just(e.getMessage()), String.class));
    }

    private Mono<UserDto> saveUser(UserDtoForCreate user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<UserDtoForCreate>> violations = validator.validate(user);
        if (!violations.isEmpty()) {
            return Mono.error(new ConstraintViolationException(violations));
        }
        return repository
                .findByEmail(user.getEmail())
                .flatMap(u -> Mono.error(new AlreadyExistsUserException(MessageFormat.format("User with email - {0} already exists", u.getEmail()))))
                .switchIfEmpty(Mono.just(user))
                .flatMap(u -> {
                    log.info("User for create {}", u);
                    user.setPassword(encoder.encode(user.getPassword()));
                    return repository.save(mapper.toEntityForCreate(user).addRole(RoleType.ROLE_USER)).map(mapper::toDto);
                });
    }

    public Mono<ServerResponse> updateUser(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserDto.class).flatMap(this::checkPrincipal)
                .flatMap(user -> repository.existsByEmail(user.getEmail())
                                .flatMap(exists -> {
                                    if (exists) {
                                        log.info("User for update {}", user);
                                        return ServerResponse.accepted().body(Mono.just(user), UserDto.class);
                                    }
                                    log.info("User with email - {} not found", user.getEmail());
                                    return ServerResponse.notFound().build();
                                })
                )
                .onErrorResume(e -> ServerResponse.status(HttpStatus.FORBIDDEN).body(Mono.just(e.getMessage()), String.class));
    }

    public Mono<ServerResponse> addUserRole(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(UserDtoForAddRole.class)
                .flatMap(user -> repository.existsByEmail(user.getEmail())
                        .flatMap(exists -> {
                            if (exists) {
                                log.info("User with email - {} add role - {}", user.getEmail(), user.getRole().name());
                                return ServerResponse.accepted().body(
                                        repository.findByEmail(user.getEmail())
                                                .map(usr -> usr.addRole(user.getRole()))
                                                .flatMap(repository::save)
                                                .map(mapper::toDto),
                                        UserDto.class
                                );
                            }
                            log.info("User with email - {} not found", user.getEmail());
                            return ServerResponse.notFound().build();
                        })
                );
    }

    public Mono<ServerResponse> deleteUserByEmail(ServerRequest serverRequest) {
        String email = serverRequest.pathVariable("email");
        UserDto user = new UserDto();
        user.setEmail(email);
        return checkPrincipal(user).map(UserDto::getEmail)
                .flatMap(repository::existsByEmail)
                .flatMap(exists -> {
                    if (exists) {
                        log.info("Delete user with email - {}", email);
                        return ServerResponse
                                .noContent()
                                .build(repository.deleteByEmail(email));
                    }
                    log.info("User with email - {} is not found", email);
                    return ServerResponse
                            .notFound()
                            .build();
                })
                .onErrorResume(e -> ServerResponse.status(HttpStatus.FORBIDDEN).body(Mono.just(e.getMessage()), String.class));
    }

    public Mono<UserDetails> getUserDetailsByEmail(String email) {
        return repository.findByEmail(email).map(AppUserPrincipal::new);
    }

    private Mono<UserDto> checkPrincipal(UserDto user) {
        return detailsService.getPrincipal()
                .flatMap(principal -> {
                    if (principal.getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .anyMatch(authority -> authority.equals("ROLE_USER")) && !principal.getUsername().equals(user.getEmail())) {
                        return Mono.error(new AccessDeniedException(MessageFormat.format("User with email {0} does not have the rights to update another user data!", user.getEmail())));
                    }
                    return Mono.just(user);
                });
    }
}
