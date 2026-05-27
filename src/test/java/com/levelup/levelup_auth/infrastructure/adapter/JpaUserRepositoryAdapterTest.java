package com.levelup.levelup_auth.infrastructure.adapter;

import com.levelup.levelup_auth.domain.model.Login;
import com.levelup.levelup_auth.domain.model.Password;
import com.levelup.levelup_auth.domain.model.User;
import com.levelup.levelup_auth.domain.model.UserId;
import com.levelup.levelup_auth.infrastructure.persistence.mapper.UserMapper;
import com.levelup.levelup_auth.infrastructure.persistence.repository.UserJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(UserMapper.class)
@Testcontainers
class JpaUserRepositoryAdapterTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.liquibase.enabled", () -> "true");
    }

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserMapper userMapper;

    private JpaUserRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new JpaUserRepositoryAdapter(userJpaRepository, userMapper);
        userJpaRepository.deleteAll();
    }

    @Test
    void shouldSaveAndFindUserById() {
        UserId id = UserId.generate();
        Login login = new Login("dbTestUser");
        Password password = new Password("hashedPassword");
        User user = new User(id, login, password);

        adapter.save(user);
        Optional<User> foundUser = adapter.findById(id);

        assertTrue(foundUser.isPresent());
        assertEquals(id, foundUser.get().getId());
        assertEquals(login.value(), foundUser.get().getLogin().value());
    }

    @Test
    void shouldSaveAndFindUserByLogin() {
        UserId id = UserId.generate();
        Login login = new Login("loginSearchUser");
        Password password = new Password("hashedPassword");
        User user = new User(id, login, password);

        adapter.save(user);
        Optional<User> foundUser = adapter.findByLogin(login);

        assertTrue(foundUser.isPresent());
        assertEquals(id, foundUser.get().getId());
        assertEquals(login.value(), foundUser.get().getLogin().value());
    }

    @Test
    void shouldReturnTrueWhenUserExistsByLogin() {
        UserId id = UserId.generate();
        Login login = new Login("existingUser");
        Password password = new Password("hashedPassword");
        User user = new User(id, login, password);

        adapter.save(user);
        boolean exists = adapter.existsByLogin(login);

        assertTrue(exists);
    }

    @Test
    void shouldReturnFalseWhenUserDoesNotExistByLogin() {
        Login login = new Login("nonExistentUser");

        boolean exists = adapter.existsByLogin(login);

        assertFalse(exists);
    }
}