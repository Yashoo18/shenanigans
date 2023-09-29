package com.random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService service;
    @MockBean
    private UserRepository repository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void selectAllCustomers() {
        when(repository.findAll()).thenReturn(Stream
                .of(new User(376L, "Danile", "danile@gmail.com"), new User(958L, "Huy","huy@gmail.com")).collect(Collectors.toList()));
        assertEquals(2, service.selectAllCustomers().size());
    }

    @Test
    void createUser() {
        User user = new User(1L,"daniel","daniel@gmail.com");
        when(repository.save(user)).thenReturn(user);
        assertEquals(user,service.createUser(user));
    }

    @Test
    void findByUsername() {
        String username = "Danile";
        when(repository.findAll()).thenReturn(Stream.of(new User(376L, "Danile", "danile@gmail.com")).collect(Collectors.toList()));
        assertEquals(1,service.findByUsername(username).size());
    }

    @Test
    void API() {
        User user = new User(1L,"daniel","daniel@gmail.com");
        when(repository.save(user)).thenReturn(user);
        assertEquals(user,service.createUser(user));
    }
}