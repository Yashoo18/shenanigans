package com.random.shenanigans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Repository("jpa")
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void selectAllCustomers() {
        System.out.println(userRepository.findAll(Pageable.ofSize(1000)));
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findByUsername(String username) {
        return userRepository.findAll();
    }
}