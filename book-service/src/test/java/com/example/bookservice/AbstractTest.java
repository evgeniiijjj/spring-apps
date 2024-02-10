package com.example.bookservice;

import com.example.bookservice.repositories.BookRepository;
import com.example.bookservice.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.redis.testcontainers.RedisContainer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Sql("classpath:db/init.sql")
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Testcontainers
public class AbstractTest {

	@Autowired
	protected MockMvc mockMvc;
	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;
	@Autowired
	protected ObjectMapper objectMapper;
	@Autowired
	protected BookService bookService;
	@Autowired
	protected BookRepository bookRepository;

	@Container
	protected static final PostgreSQLContainer POSTGRE_SQL_CONTAINER = (PostgreSQLContainer) new PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
			.withExposedPorts(5432)
			.withReuse(true);

	@Container
	protected static final RedisContainer REDIS_CONTAINER = new RedisContainer(DockerImageName.parse("redis:latest"))
			.withExposedPorts(6379)
			.withReuse(true);

	@DynamicPropertySource
	public static void registerProperties(DynamicPropertyRegistry registry) {

		registry.add("spring.datasource.username", POSTGRE_SQL_CONTAINER::getUsername);
		registry.add("spring.datasource.password", POSTGRE_SQL_CONTAINER::getPassword);
		registry.add("spring.datasource.url", POSTGRE_SQL_CONTAINER::getJdbcUrl);

		registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
		registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(6379).toString());
	}

	@BeforeEach
	public void before() {
		redisTemplate.getConnectionFactory().getConnection().serverCommands().flushAll();
	}
}
