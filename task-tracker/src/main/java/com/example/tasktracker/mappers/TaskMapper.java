package com.example.tasktracker.mappers;

import com.example.tasktracker.dtos.TaskDto;
import com.example.tasktracker.dtos.UserDto;
import com.example.tasktracker.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDto toDto(Task task);
    @Mapping(target = "authorId", source = "author.id")
    @Mapping(target = "assigneeId", source = "assignee.id")
    @Mapping(target = "observerIds", source = "observers", qualifiedBy = ObserverIdsMapper.class)
    Task toEntity(TaskDto task);
    @ObserverIdsMapper
    static List<String> getObserverIds(List<UserDto> observers) {
        return observers.stream().map(UserDto::getId).toList();
    }
}
