package com.example.tasktracker.handlers;

import com.example.tasktracker.dtos.TaskDto;
import com.example.tasktracker.dtos.UserDto;
import com.example.tasktracker.mappers.TaskMapper;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@RequiredArgsConstructor
@Component
@Slf4j
public class TaskHandler {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper mapper;

    public Mono<ServerResponse> getAllTasks(ServerRequest serverRequest) {
        log.info("Get all tasks");
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(taskRepository.findAll()
                        .flatMap(task -> userRepository.findById(task.getAuthorId())
                                .map(task::setAuthor))
                        .flatMap(task -> userRepository.findById(task.getAssigneeId())
                                .map(task::setAssignee))
                        .flatMap(task -> userRepository.findAllById(task.getObserverIds()).buffer().map(task::setObservers))
                        .map(mapper::toDto), TaskDto.class);
    }

    public Mono<ServerResponse> getTaskById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return taskRepository
                .findById(id)
                .flatMap(task -> userRepository.findById(task.getAuthorId()).map(task::setAuthor))
                .flatMap(task -> userRepository.findById(task.getAssigneeId()).map(task::setAssignee))
                .flatMap(task -> Mono.from(userRepository.findAllById(task.getObserverIds()).buffer()).map(task::setObservers))
                .map(mapper::toDto)
                .flatMap(task -> {
                    log.info("Get task by id - {}", id);
                    return ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(task), TaskDto.class);
                })
                .switchIfEmpty(
                        ServerResponse
                            .notFound()
                            .build()
                );
    }

    public Mono<ServerResponse> createTask(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TaskDto.class)
                .map(task -> task.setCreatedAt(Instant.now().truncatedTo(ChronoUnit.SECONDS))
                        .setUpdatedAt(Instant.now().truncatedTo(ChronoUnit.SECONDS)))
                .flatMap(task -> {
                    log.info("Task for create {}", task);
                    return taskRepository.save(mapper.toEntity(task)).map(mapper::toDto);
                })
                .flatMap(task -> ServerResponse.created(URI.create("api/tasks/task/" + task.getId())).body(Mono.just(task), TaskDto.class));
    }

    public Mono<ServerResponse> updateTask(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(TaskDto.class)
                .map(task -> task.setUpdatedAt(Instant.now().truncatedTo(ChronoUnit.SECONDS)))
                .flatMap(task -> taskRepository.existsById(task.getId())
                        .flatMap(exists -> {
                            if(exists) {
                                log.info("Task for update {}", task);
                                return ServerResponse.accepted().body(taskRepository.save(mapper.toEntity(task)).map(mapper::toDto), TaskDto.class);
                            }
                            log.info("Task with id {} not found", task.getId());
                            return ServerResponse.notFound().build();
                        })
                );
    }

    public Mono<ServerResponse> addTaskObserver(ServerRequest serverRequest) {
        var taskId = serverRequest.pathVariable("id");
        var observer = serverRequest.bodyToMono(UserDto.class);
        return observer
                .flatMap(user -> taskRepository.findById(taskId).flatMap(task -> userRepository.findById(user.getId()).map(task::addObserver)))
                .flatMap(taskRepository::save)
                .flatMap(task -> userRepository.findById(task.getAuthorId()).map(task::setAuthor))
                .flatMap(task -> userRepository.findById(task.getAssigneeId()).map(task::setAssignee))
                .flatMap(task -> Mono.from(userRepository.findAllById(task.getObserverIds()).buffer()).map(task::setObservers))
                .map(mapper::toDto)
                .flatMap(task -> {
                    log.info("Observer is successfully added for task - {}", task);
                    return ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Mono.just(task), TaskDto.class);

                })
                .switchIfEmpty(
                        ServerResponse
                                .notFound()
                                .build()
                );
    }

    public Mono<ServerResponse> deleteTaskById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return taskRepository
                .existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        log.info("Delete task with id - {}", id);
                        return ServerResponse
                                .noContent()
                                .build(taskRepository.deleteById(id));
                    }
                    log.info("Task with id - {} is not found", id);
                    return ServerResponse
                            .notFound()
                            .build();
                });
    }
}
