package com.random.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.random.data.User;
import com.random.repo.UserRepository;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    public UserService(UserRepository userRepository, RestTemplate restTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
    }

    public List<User> selectAllCustomers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> findByUsername(String username) {
        return userRepository.findAll().stream().filter(user -> username.equals(user.getUsername())).collect(Collectors.toList());
    }

    public User API() {
        JsonObject jsonObject = null;
        String apiUrl = "https://randomuser.me/api/";
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
        Gson gson = new Gson();
        jsonObject = gson.fromJson(response.getBody(), JsonObject.class);
        assert jsonObject != null;
        String name = jsonObject.get("results").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsJsonObject().get("first").getAsString();
        String email = name + "@gmail.com";
        System.out.println(name + ": email :" + email);
        User user = new User(null, name, email);
        userRepository.save(user);
        return user;
    }
}