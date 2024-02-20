package com.example.tasktracker.dtos;

import com.example.tasktracker.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskDto implements Comparable<TaskDto> {
    private String id;
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private TaskStatus status;
    private UserDto author;
    private UserDto assignee;
    private List<UserDto> observers;

    public TaskDto setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public TaskDto setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setObservers(List<UserDto> observers) {
        if (observers != null) {
            observers.sort(UserDto::compareTo);
        }
        this.observers = observers;
    }

    @Override
    public int compareTo(TaskDto o) {
        return name.compareTo(o.name);
    }
}
