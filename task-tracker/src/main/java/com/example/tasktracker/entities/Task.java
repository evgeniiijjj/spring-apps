package com.example.tasktracker.entities;

import com.example.tasktracker.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.ReadOnlyProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tasks")
public class Task {
    @Id
    private String id;
    private String name;
    private String description;
    private Instant createdAt;
    private Instant updatedAt;
    private TaskStatus status;
    private String authorEmail;
    private String assigneeEmail;
    private List<String> observerEmails;
    @ReadOnlyProperty
    private User author;
    @ReadOnlyProperty
    private User assignee;
    @ReadOnlyProperty
    private List<User> observers;

    public Task setAuthor(User author) {
        this.author = author;
        return this;
    }

    public Task setAssignee(User assignee) {
        this.assignee = assignee;
        return this;
    }

    public Task setObservers(List<User> observers) {
        this.observers = observers;
        return this;
    }

    public Task addObserver(User user) {
        observerEmails.add(user.getEmail());
        return this;
    }
}
