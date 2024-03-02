package com.example.tasktracker.mappers;

import com.example.tasktracker.dtos.TaskDto;
import com.example.tasktracker.dtos.UserDto;
import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);
    @Mapping(target = "author.password", ignore = true)
    @Mapping(target = "author.roles", ignore = true)
    @Mapping(target = "assignee.password", ignore = true)
    @Mapping(target = "assignee.roles", ignore = true)
    @Mapping(target = "observers", ignore = true)
    @Mapping(target = "authorEmail", source = "author.email")
    @Mapping(target = "assigneeEmail", source = "assignee.email")
    @Mapping(target = "observerEmails", source = "observers", qualifiedBy = ObserverEmailsMapper.class)
    Task toEntity(TaskDto task);
    @ObserverEmailsMapper
    static List<String> getObserverEmails(List<UserDto> observers) {
        return observers.stream().map(UserDto::getEmail).toList();
    }
}
