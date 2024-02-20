package com.example.tasktracker;

import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.enums.TaskStatus;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Testcontainers
@AutoConfigureWebTestClient
public class AbstractTest {

	protected static String FIRST_USER_ID = UUID.randomUUID().toString();
	protected static String SECOND_USER_ID = UUID.randomUUID().toString();
	protected static String THIRD_USER_ID = UUID.randomUUID().toString();
	protected static String FOURTH_ID = UUID.randomUUID().toString();
	protected static String FIFTH_ID = UUID.randomUUID().toString();
	protected static String FIRST_TASK_ID = UUID.randomUUID().toString();
	protected static String SECOND_TASK_ID = UUID.randomUUID().toString();
	protected static String THIRD_TASK_ID = UUID.randomUUID().toString();
	protected static Instant NOW = Instant.now().truncatedTo(ChronoUnit.SECONDS);

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest")
			.withReuse(true);

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.data.mongo.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Autowired
	protected WebTestClient webTestClient;
	@Autowired
	protected UserRepository userRepository;
	@Autowired
	protected TaskRepository taskRepository;

	@BeforeEach
	public void setup() {

		userRepository.deleteAll().block();
		taskRepository.deleteAll().block();

		var firstUser = new User(FIRST_USER_ID, "Bill", "bill@gmail.com");
		var secondUser = new User(SECOND_USER_ID, "John", "john@gmail.com");
		var thirdUser = new User(THIRD_USER_ID, "Alex", "alex@gmail.com");
		var fourthUser = new User(FOURTH_ID, "Eugine", "eugine@gmail.com");
		var fifthUser = new User(FIFTH_ID, "Paul", "paul@gmail.com");
		var firstTask = new Task(FIRST_TASK_ID, "task_1", "description_task_1", NOW, NOW, TaskStatus.TODO, firstUser.getId(), secondUser.getId(), List.of(thirdUser.getId(), fourthUser.getId()), null, null, null);
		var secondTask = new Task(SECOND_TASK_ID, "task_2", "description_task_2", NOW, NOW, TaskStatus.TODO, secondUser.getId(), thirdUser.getId(), List.of(firstUser.getId(), fourthUser.getId()), null, null, null);
		var thirdTask = new Task(THIRD_TASK_ID, "task_3", "description_task_3", NOW, NOW, TaskStatus.TODO, thirdUser.getId(), fourthUser.getId(), List.of(secondUser.getId(), fifthUser.getId()), null, null, null);

		userRepository.saveAll(
			List.of(firstUser, secondUser, thirdUser, fourthUser, fifthUser)
		).collectList().block();

		taskRepository.saveAll(
			List.of(firstTask, secondTask, thirdTask)
		).collectList().block();
	}
}
