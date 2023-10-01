package com.random;

import com.random.data.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestH2Repo repo;
    private static RestTemplate restTemplate;
    String baseUrl = "http://localhost:";

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(port + "").concat("/api");
    }

    @Test
    public void createUserTest() {
        User user = new User(1L, "dany", "dany@gmail.com");
        User test = restTemplate.postForObject(baseUrl + "/create", user, User.class);
        assert test != null;
        assertEquals("dany", test.getUsername());
        assertEquals(1, repo.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO USERS (id,username, email) VALUES (4L,'dany', 'dany@gmail.com')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM USERS WHERE USERNAME = 'dany'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void selectAllCustomersTest() {
        List<User> test = restTemplate.getForObject(baseUrl + "/getall", List.class);
        assert test != null;
        assertEquals(test.size(), repo.findAll().size());
        assertEquals(1, repo.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO USERS (id,username, email) VALUES (4L,'dany', 'dany@gmail.com')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM USERS WHERE USERNAME = 'dany'", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findByUsernameTest() {
        List<User> test = restTemplate.getForObject(baseUrl + "/getbyuser" + "/{username}", List.class, "dany");
        assertNotNull(test);
        assertEquals(1, test.size());
    }

    @Test
    public void insertRandomTest() {
        int expected_size = repo.findAll().size() + 1;
        User test = restTemplate.getForObject(baseUrl + "/insertrandom", User.class);
        assertNotNull(test);
        assertEquals(expected_size, repo.findAll().size());
    }
}
