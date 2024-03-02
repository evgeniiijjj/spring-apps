package com.example.tasktracker.configurations;

import com.example.tasktracker.handlers.TaskHandler;
import com.example.tasktracker.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class TaskTrackerConfigurations {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService,
                                                               PasswordEncoder passwordEncoder) {
        var authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http, ReactiveAuthenticationManager authenticationManager) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(auth ->
                        auth
                                .pathMatchers(HttpMethod.GET, "/api/users", "/api/users/**")
                                .hasAnyRole("MANAGER", "USER")
                                .pathMatchers(HttpMethod.PUT, "/api/users/user")
                                .hasAnyRole("MANAGER", "USER")
                                .pathMatchers(HttpMethod.PUT, "/api/users/user/role")
                                .hasAnyRole("MANAGER")
                                .pathMatchers(HttpMethod.GET, "/api/tasks", "/api/tasks/**")
                                .hasAnyRole("MANAGER", "USER")
                                .pathMatchers(HttpMethod.POST, "/api/tasks/**")
                                .hasAnyRole("MANAGER")
                                .pathMatchers(HttpMethod.PUT, "/api/tasks/task")
                                .hasAnyRole("MANAGER")
                                .pathMatchers(HttpMethod.PUT, "/api/tasks/task/id")
                                .hasAnyRole("MANAGER", "USER")
                                .pathMatchers(HttpMethod.DELETE, "/api/tasks/**")
                                .hasAnyRole("MANAGER")
                                .pathMatchers(HttpMethod.POST, "api/users/user")
                                .permitAll().anyExchange().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .authenticationManager(authenticationManager)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> userRouters(UserHandler handler) {
        return RouterFunctions.route()
                .GET("/api/users", handler::getAllUsers)
                .GET("/api/users/user/{email}", handler::getUserByEmail)
                .POST("/api/users/user", handler::createUser)
                .PUT("/api/users/user", handler::updateUser)
                .PUT("/api/users/user/role", handler::addUserRole)
                .DELETE("/api/users/user/{email}", handler::deleteUserByEmail)
                .build();
    }

    @Bean
    public RouterFunction<ServerResponse> taskRouters(TaskHandler handler) {
        return RouterFunctions.route()
                .GET("/api/tasks", handler::getAllTasks)
                .GET("/api/tasks/task/{id}", handler::getTaskById)
                .POST("/api/tasks/task", handler::createTask)
                .PUT("/api/tasks/task", handler::updateTask)
                .PUT("/api/tasks/task/{id}", handler::addTaskObserver)
                .DELETE("/api/tasks/task/{id}", handler::deleteTaskById)
                .build();
    }
}
