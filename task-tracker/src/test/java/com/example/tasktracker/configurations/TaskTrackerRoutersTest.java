package com.example.tasktracker.configurations;

import com.example.tasktracker.AbstractTest;
import com.example.tasktracker.dtos.TaskDto;
import com.example.tasktracker.dtos.UserDto;
import com.example.tasktracker.dtos.UserDtoForAddRole;
import com.example.tasktracker.dtos.UserDtoForCreate;
import com.example.tasktracker.enums.RoleType;
import com.example.tasktracker.enums.TaskStatus;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TaskTrackerRoutersTest extends AbstractTest {

    static UserDto FIRST_USER = new UserDto("Bill", "bill@gmail.com");
    static UserDto SECOND_USER = new UserDto("John", "john@gmail.com");
    static UserDto THIRD_USER = new UserDto("Alex", "alex@gmail.com");
    static UserDto FOURTH_USER = new UserDto("Eugine", "eugine@gmail.com");
    static UserDto FIFTH_USER = new UserDto("Paul", "paul@gmail.com");

    @Test
    public void whenGetAllUsers_thenReturnListOfUsersFromDatabase() {

        var expectedData = List.of(
                FIFTH_USER, SECOND_USER, THIRD_USER, FOURTH_USER, FIFTH_USER
        );

        webTestClient.get().uri("/api/users")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("alex@gmail.com", "alex"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .hasSize(5)
                .contains(expectedData.toArray(UserDto[]::new));
    }

    @Test
    public void whenGetUserByEmail_thenReturnUserFromDatabase() {
        var expectedData = FIRST_USER;

        webTestClient.get().uri("/api/users/user/bill@gmail.com")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("alex@gmail.com", "alex"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .hasSize(1)
                .contains(expectedData);
    }

    @Test
    public void whenGetUserByIdAndUserWithSuchIdNotExists_thenReturnNotFoundStatus() {

        webTestClient.get().uri("/api/users/user/billy@gmail.com")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("alex@gmail.com", "alex"))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void whenCreateUser_thenReturnNewUserFromDatabase() {
        var newUser = new UserDtoForCreate("Nick", "nick@gmail.com", "nick");

        StepVerifier.create(userRepository.count())
                        .expectNext(5L)
                        .expectComplete()
                        .verify();

        webTestClient.post().uri("/api/users/user")
                .body(Mono.just(newUser), UserDtoForCreate.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserDto.class)
                .value(user -> {
                        assertEquals(user, new UserDto("Nick", "nick@gmail.com"));
                });

        StepVerifier.create(userRepository.count())
                .expectNext(6L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenCreateUserWithInvalidFieldValues_thenReturnBadRequestStatus() {
        var newUser = new UserDtoForCreate("", "nick@gmail.com", "");

        webTestClient.post().uri("/api/users/user")
                .body(Mono.just(newUser), UserDtoForCreate.class)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    public void whenUpdateUser_thenReturnUpdatedUserFromDatabase() {
        var updatedUser =  new UserDto("Alex", "alex@gmail.com");

        webTestClient.put().uri("/api/users/user")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("alex@gmail.com", "alex"))
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
        var updatedUser =  new UserDto("Will", "will@gmail.com");

        webTestClient.put().uri("/api/users/user")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("alex@gmail.com", "alex"))
                .body(Mono.just(updatedUser), UserDto.class)
                .exchange()
                .expectStatus().isForbidden();

        StepVerifier.create(userRepository.count())
                .expectNext(5L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenUpdateUserByOtherUser_thenReturnForbiddenStatus() {
        var updatedUser =  new UserDto("Bill", "bill@gmail.com");

        webTestClient.put().uri("/api/users/user")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("alex@gmail.com", "alex"))
                .body(Mono.just(updatedUser), UserDto.class)
                .exchange()
                .expectStatus().isForbidden();

        StepVerifier.create(userRepository.count())
                .expectNext(5L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenAddUserRoleByUserWithRoleManager_thenReturnAcceptedStatus() {
        var updatedUser =  new UserDtoForAddRole("alex@gmail.com", RoleType.ROLE_MANAGER);

        webTestClient.put().uri("/api/users/user/role")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("bill@gmail.com", "bill"))
                .body(Mono.just(updatedUser), UserDtoForAddRole.class)
                .exchange()
                .expectStatus().isAccepted();
    }

    @Test
    public void whenAddUserRoleByUserWithRoleNoManager_thenReturnForbiddenStatus() {
        var updatedUser =  new UserDtoForAddRole("bill@gmail.com", RoleType.ROLE_MANAGER);

        webTestClient.put().uri("/api/users/user/role")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("alex@gmail.com", "alex"))
                .body(Mono.just(updatedUser), UserDto.class)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void whenDeleteUserById_thenRemoveUserFromDatabase() {

        StepVerifier.create(userRepository.count())
                .expectNext(5L)
                .expectComplete()
                .verify();

        webTestClient.delete().uri("/api/users/user/alex@gmail.com")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("alex@gmail.com", "alex"))
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

        webTestClient.delete().uri("/api/users/user/billy@gmail.com")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("bill@gmail.com", "bill"))
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
                .headers(httpHeaders -> httpHeaders.setBasicAuth("alex@gmail.com", "alex"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(TaskDto.class)
                .hasSize(3)
                .contains(expectedData.toArray(TaskDto[]::new));
    }

    @Test
    public void whenGetTaskByIdAndIdNotExistent_thenReturnNotFoundStatus() {

        webTestClient.get().uri("/api/tasks/task/" + FIRST_TASK_ID + "asdf")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("alex@gmail.com", "alex"))
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void whenCreateTaskByUserWithRoleManager_thenReturnNewTaskFromDatabase() {
        var newTask = new TaskDto(null, "task_4", "description_task_4", null, null, TaskStatus.TODO, FIRST_USER, SECOND_USER, List.of(THIRD_USER, FOURTH_USER));

        StepVerifier.create(taskRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();

        webTestClient.post().uri("/api/tasks/task")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("bill@gmail.com", "bill"))
                .body(Mono.just(newTask), TaskDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(TaskDto.class)
                .value(task -> {
                    assertNotNull(task.getId());
                    newTask.setId(task.getId());
                    newTask.setCreatedAt(task.getCreatedAt());
                    newTask.setUpdatedAt(task.getUpdatedAt());
                    assertEquals(newTask, task);
                });

        StepVerifier.create(taskRepository.count())
                .expectNext(4L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenCreateTaskByUserWithRoleNoManager_thenReturnForbiddenStatus() {
        var newTask = new TaskDto(null, "task_4", "description_task_4", null, null, TaskStatus.TODO, FIRST_USER, SECOND_USER, List.of(THIRD_USER, FOURTH_USER));

        webTestClient.post().uri("/api/tasks/task")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("alex@gmail.com", "alex"))
                .body(Mono.just(newTask), TaskDto.class)
                .exchange()
                .expectStatus().isForbidden();
    }

    @Test
    public void whenUpdateTask_thenReturnUpdatedTaskFromDatabase() {
        var updatedTask = new TaskDto(FIRST_TASK_ID, "task_for_update", "description_task_for_update", NOW, NOW, TaskStatus.TODO, FIRST_USER, SECOND_USER, List.of(THIRD_USER, FOURTH_USER));

        webTestClient.put().uri("/api/tasks/task")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("bill@gmail.com", "bill"))
                .body(Mono.just(updatedTask), TaskDto.class)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody(TaskDto.class)
                .value(task -> {
                        updatedTask.setUpdatedAt(task.getUpdatedAt());
                        assertEquals(updatedTask, task);
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
                .headers(httpHeaders -> httpHeaders.setBasicAuth("bill@gmail.com", "bill"))
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
        var updatedTask = new TaskDto(FIRST_TASK_ID, "task_1", "description_task_1", NOW, NOW, TaskStatus.TODO, FIRST_USER, SECOND_USER, List.of(THIRD_USER, FOURTH_USER, FIFTH_USER));

        webTestClient.put().uri("/api/tasks/task/" + FIRST_TASK_ID)
                .headers(httpHeaders -> httpHeaders.setBasicAuth("alex@gmail.com", "alex"))
                .body(Mono.just(FIFTH_USER), UserDto.class)
                .exchange()
                .expectStatus().isAccepted()
                .expectBody(TaskDto.class)
                .value(task -> {
                    updatedTask.setUpdatedAt(task.getUpdatedAt());
                    assertEquals(updatedTask, task);
                });

        StepVerifier.create(taskRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();
    }

    @Test
    public void whenAddTaskObserverAndIdNotExist_thenReturnNotFoundStatus() {
        var updatedTask = new TaskDto(FIRST_TASK_ID + "asdf", "task_for_update", "description_task_for_update", NOW, NOW, TaskStatus.TODO, FIRST_USER, SECOND_USER, List.of(THIRD_USER, FOURTH_USER, FIFTH_USER));

        webTestClient.put().uri("/api/tasks/task?taskId=" + FIRST_TASK_ID + "&userId=" + FIFTH_USER.getEmail() + "adsf")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("bill@gmail.com", "bill"))
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
                .headers(httpHeaders -> httpHeaders.setBasicAuth("bill@gmail.com", "bill"))
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
                .headers(httpHeaders -> httpHeaders.setBasicAuth("bill@gmail.com", "bill"))
                .exchange()
                .expectStatus().isNotFound();

        StepVerifier.create(taskRepository.count())
                .expectNext(3L)
                .expectComplete()
                .verify();
    }
}
