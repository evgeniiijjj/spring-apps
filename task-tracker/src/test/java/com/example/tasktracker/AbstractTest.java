package com.example.tasktracker;

import com.example.tasktracker.entities.Task;
import com.example.tasktracker.entities.User;
import com.example.tasktracker.enums.RoleType;
import com.example.tasktracker.enums.TaskStatus;
import com.example.tasktracker.repositories.TaskRepository;
import com.example.tasktracker.repositories.UserRepository;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@SpringBootTest
@Testcontainers
@AutoConfigureWebTestClient
public class AbstractTest {

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

		var firstUser = new User("bill@gmail.com", "Bill", "$2a$12$MKNw2qxjcgSeJ89xD5n.3OX03rsS.M8mTfMmTt24SWEkxHBEuR9gK", Set.of(RoleType.ROLE_MANAGER));
		var secondUser = new User("john@gmail.com", "John", "$2a$12$A9K1ZDnFHCjJjZ0LcbVeQuYbbRLrqBBBsKQrUPOvO6qJ7zRCP/1Uu", Set.of(RoleType.ROLE_USER));
		var thirdUser = new User("alex@gmail.com", "Alex", "$2a$12$W5QlGgGdiGMh8vjV9sf.wezXNcWMjYTTuefqQWMFbrnQWLYAl6TRu", Set.of(RoleType.ROLE_USER));
		var fourthUser = new User("eugine@gmail.com", "Eugine", "$2a$12$Z3Bo9AUOgSogQROrPLq2JupIpoGi27kc.wDnXhruVYT/pbLhS1eLi", Set.of(RoleType.ROLE_USER));
		var fifthUser = new User("paul@gmail.com", "Paul", "$2a$12$GS9f4hwfOat78YO.DuLPguYyiOhSMdSxBygEypsIyyG.y2MNkT9qm", Set.of(RoleType.ROLE_USER));
		var firstTask = new Task(FIRST_TASK_ID, "task_1", "description_task_1", NOW, NOW, TaskStatus.TODO, firstUser.getEmail(), secondUser.getEmail(), List.of(thirdUser.getEmail(), fourthUser.getEmail()), null, null, null);
		var secondTask = new Task(SECOND_TASK_ID, "task_2", "description_task_2", NOW, NOW, TaskStatus.TODO, secondUser.getEmail(), thirdUser.getEmail(), List.of(firstUser.getEmail(), fourthUser.getEmail()), null, null, null);
		var thirdTask = new Task(THIRD_TASK_ID, "task_3", "description_task_3", NOW, NOW, TaskStatus.TODO, thirdUser.getEmail(), fourthUser.getEmail(), List.of(secondUser.getEmail(), fifthUser.getEmail()), null, null, null);

		userRepository.saveAll(
			List.of(firstUser, secondUser, thirdUser, fourthUser, fifthUser)
		).collectList().block();

		taskRepository.saveAll(
			List.of(firstTask, secondTask, thirdTask)
		).collectList().block();
	}
}
