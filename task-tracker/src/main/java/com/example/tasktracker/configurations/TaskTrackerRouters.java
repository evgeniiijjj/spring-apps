package com.example.tasktracker.configurations;

import com.example.tasktracker.handlers.TaskHandler;
import com.example.tasktracker.handlers.UserHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TaskTrackerRouters {

    @Bean
    public RouterFunction<ServerResponse> userRouters(UserHandler handler) {
        return RouterFunctions.route()
                .GET("/api/users", handler::getAllUsers)
                .GET("/api/users/user/{id}", handler::getUserById)
                .POST("/api/users/user", handler::createUser)
                .PUT("/api/users/user", handler::updateUser)
                .DELETE("/api/users/user/{id}", handler::deleteUserById)
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
