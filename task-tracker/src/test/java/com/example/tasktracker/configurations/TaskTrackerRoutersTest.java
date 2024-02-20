package com.example.tasktracker.configurations;

import com.example.tasktracker.AbstractTest;
import com.example.tasktracker.dtos.TaskDto;
import com.example.tasktracker.dtos.UserDto;
import com.example.tasktracker.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTrackerRoutersTest extends AbstractTest {

    static UserDto FIRST_USER = new UserDto(FIRST_USER_ID, "Bill", "bill@gmail.com");
    static UserDto SECOND_USER = new UserDto(SECOND_USER_ID, "John", "john@gmail.com");
    static UserDto THIRD_USER = new UserDto(THIRD_USER_ID, "Alex", "alex@gmail.com");
    static UserDto FOURTH_USER = new UserDto(FOURTH_ID, "Eugine", "eugine@gmail.com");
    static UserDto FIFTH_USER = new UserDto(FIFTH_ID, "Paul", "paul@gmail.com");

    @Test
    public void whenGetAllUsers_thenReturnListOfUsersFromDatabase() {

        var expectedData = List.of(
                new UserDto(FIRST_USER_ID, "Bill", "bill@gmail.com"),
                new UserDto(SECOND_USER_ID, "John", "john@gmail.com"),
                new UserDto(THIRD_USER_ID, "Alex", "alex@gmail.com"),
                new UserDto(FOURTH_ID, "Eugine", "eugine@gmail.com"),
                new UserDto(FIFTH_ID, "Paul", "paul@gmail.com")
        );

        webTestClient.get().uri("/api/users")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .hasSize(5)
                .contains(expectedData.toArray(UserDto[]::new));
    }

    @Test
    public void whenGetUserById_thenReturnUserFromDatabase() {
        var expectedData = new UserDto(FIRST_USER_ID, "Bill", "bill@gmail.com");

        webTestClient.get().uri("/api/users/user/" + FIRST_USER_ID)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .hasSize(1)
                .contains(expectedData);
    }

    @Test
    public void whenGetUserByIdAndUserWithSuchIdNotExists_thenReturnNotFoundStatus() {

        webTestClient.get().uri("/api/users/user/" + FIRST_USER_ID + "asdf")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void whenCreateUser_thenReturnNewUserFromDatabase() {
        var newUser = new UserDto(null, "Nick", "nick@gmail.com");

        StepVerifier.create(userRepository.count())
                        .expectNext(5L)
                        .expectComplete()
                        .verify();

        webTestClient.post().uri("/api/users/user")
                .body(Mono.just(newUser), UserDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserDto.class)
                .value(user -> {
                        assertNotNull(user.getId());
                        newUser.setId(user.getId());
                        assertEquals(user, newUser);
                });

        StepVerifier.create(userRepository.count())
                .expectNext(6L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenUpdateUser_thenReturnUpdatedUserFromDatabase() {
        var updatedUser =  new UserDto(FIRST_USER_ID, "Will", "will@gmail.com");

        webTestClient.put().uri("/api/users/user")
                .body(Mono.just(updatedUser), UserDto.class)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody(UserDto.class)
                .value(user ->
                        assertEquals(user, updatedUser)
                );

        StepVerifier.create(userRepository.count())
                .expectNext(5L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenUpdateUserWithNotExistentId_thenReturnNotFoundStatus() {
        var updatedUser =  new UserDto(FIRST_USER_ID + "asdfg", "Will", "will@gmail.com");

        webTestClient.put().uri("/api/users/user")
                .body(Mono.just(updatedUser), UserDto.class)
                .exchange()
                .expectStatus().isNotFound();

        StepVerifier.create(userRepository.count())
                .expectNext(5L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenDeleteUserById_thenRemoveUserFromDatabase() {

        StepVerifier.create(userRepository.count())
                .expectNext(5L)
                .expectComplete()
                .verify();

        webTestClient.delete().uri("/api/users/user/" + FIRST_USER_ID)
                .exchange()
                .expectStatus().isNoContent();

        StepVerifier.create(userRepository.count())
                .expectNext(4L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenDeleteUserByIdAndIdNotExists_thenReturnNotFoundStatus() {

        StepVerifier.create(userRepository.count())
                .expectNext(5L)
                .expectComplete()
                .verify();

        webTestClient.delete().uri("/api/users/user/" + FIRST_USER_ID + "asdf")
                .exchange()
                .expectStatus().isNotFound();

        StepVerifier.create(userRepository.count())
                .expectNext(5L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenGetAllTasks_thenReturnListOfTasksFromDatabase() {

        var expectedData = List.of(
                new TaskDto(FIRST_TASK_ID, "task_1", "description_task_1", NOW, NOW, TaskStatus.TODO, FIRST_USER, SECOND_USER, List.of(THIRD_USER, FOURTH_USER)),
                new TaskDto(SECOND_TASK_ID, "task_2", "description_task_2", NOW, NOW, TaskStatus.TODO, SECOND_USER, THIRD_USER, List.of(FIRST_USER, FOURTH_USER)),
                new TaskDto(THIRD_TASK_ID, "task_3", "description_task_3", NOW, NOW, TaskStatus.TODO, THIRD_USER, FOURTH_USER, List.of(SECOND_USER, FIFTH_USER))
        );

        webTestClient.get().uri("/api/tasks")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TaskDto.class)
                .hasSize(3)
                .contains(expectedData.toArray(TaskDto[]::new));
    }

    @Test
    public void whenGetTaskByIdAndIdNotExistent_thenReturnNotFoundStatus() {
        var expectedData =  new TaskDto(FIRST_TASK_ID, "task_1", "description_task_1", NOW, NOW, TaskStatus.TODO, FIRST_USER, SECOND_USER, List.of(THIRD_USER, FOURTH_USER));

        webTestClient.get().uri("/api/tasks/task/" + FIRST_TASK_ID + "asdf")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void whenCreateTask_thenReturnNewTaskFromDatabase() {
        var newTask = new TaskDto(null, "task_4", "description_task_4", null, null, TaskStatus.TODO, FIRST_USER, SECOND_USER, List.of(THIRD_USER, FOURTH_USER));

        StepVerifier.create(taskRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();

        webTestClient.post().uri("/api/tasks/task")
                .body(Mono.just(newTask), TaskDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TaskDto.class)
                .value(task -> {
                    assertNotNull(task.getId());
                    newTask.setId(task.getId());
                    newTask.setCreatedAt(task.getCreatedAt());
                    newTask.setUpdatedAt(task.getUpdatedAt());
                    assertEquals(task, newTask);
                });

        StepVerifier.create(taskRepository.count())
                .expectNext(4L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenUpdateTask_thenReturnUpdatedTaskFromDatabase() {
        var updatedTask = new TaskDto(FIRST_TASK_ID, "task_for_update", "description_task_for_update", NOW, NOW, TaskStatus.TODO, FIRST_USER, SECOND_USER, List.of(THIRD_USER, FOURTH_USER));

        webTestClient.put().uri("/api/tasks/task")
                .body(Mono.just(updatedTask), TaskDto.class)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody(TaskDto.class)
                .value(task -> {
                        updatedTask.setUpdatedAt(task.getUpdatedAt());
                        assertEquals(task, updatedTask);
                });

        StepVerifier.create(taskRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenUpdateTaskWithNotExistentId_thenReturnNotFoundStatus() {
        var updatedTask = new TaskDto(FIRST_TASK_ID + "asdf", "task_for_update", "description_task_for_update", NOW, NOW, TaskStatus.TODO, FIRST_USER, SECOND_USER, List.of(THIRD_USER, FOURTH_USER));

        webTestClient.put().uri("/api/tasks/task")
                .body(Mono.just(updatedTask), TaskDto.class)
                .exchange()
                .expectStatus().isNotFound();

        StepVerifier.create(taskRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenAddTaskObserver_thenReturnUpdatedTaskFromDatabase() {
        var updatedTask = new TaskDto(FIRST_TASK_ID, "task_for_update", "description_task_for_update", NOW, NOW, TaskStatus.TODO, FIRST_USER, SECOND_USER, List.of(THIRD_USER, FOURTH_USER, FIFTH_USER));

        webTestClient.put().uri("/api/tasks/task?taskId=" + FIRST_TASK_ID + "&userId=" + FIFTH_USER.getId())
                .body(Mono.just(updatedTask), TaskDto.class)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody(TaskDto.class)
                .value(task -> {
                    updatedTask.setUpdatedAt(task.getUpdatedAt());
                    assertEquals(task, updatedTask);
                });

        StepVerifier.create(taskRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenAddTaskObserverAndIdsNotExist_thenReturnNotFoundStatus() {
        var updatedTask = new TaskDto(FIRST_TASK_ID + "asdf", "task_for_update", "description_task_for_update", NOW, NOW, TaskStatus.TODO, FIRST_USER, SECOND_USER, List.of(THIRD_USER, FOURTH_USER, FIFTH_USER));

        webTestClient.put().uri("/api/tasks/task?taskId=" + FIRST_TASK_ID + "&userId=" + FIFTH_USER.getId() + "adsf")
                .body(Mono.just(updatedTask), TaskDto.class)
                .exchange()
                .expectStatus().isNotFound();

        StepVerifier.create(taskRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenDeleteTaskById_thenRemoveTaskFromDatabase() {

        StepVerifier.create(taskRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();

        webTestClient.delete().uri("/api/tasks/task/" + FIRST_TASK_ID)
                .exchange()
                .expectStatus().isNoContent();

        StepVerifier.create(taskRepository.count())
                .expectNext(2L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenDeleteTaskByIdAndIdNotExists_thenReturnNotFoundStatus() {

        StepVerifier.create(taskRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();

        webTestClient.delete().uri("/api/tasks/task/" + FIRST_TASK_ID + "adsf")
                .exchange()
                .expectStatus().isNotFound();

        StepVerifier.create(taskRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();
    }
}
