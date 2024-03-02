package com.example.tasktracker.security;

import com.example.tasktracker.handlers.TaskHandler;
import com.example.tasktracker.handlers.UserHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Getter
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    private final UserHandler userHandler;
    private final TaskHandler taskHandler;
    private Mono<UserDetails> principal;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        principal = userHandler.getUserDetailsByEmail(username);
        userHandler.setDetailsService(this);
        taskHandler.setDetailsService(this);
        return principal;
    }
}
