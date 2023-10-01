package com.random.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.random.data.User;
import com.random.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class UserService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final QueueMessagingTemplate queueMessagingTemplate;

    Logger logger = LoggerFactory.getLogger(UserService.class);
    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    public UserService(UserRepository userRepository, RestTemplate restTemplate, QueueMessagingTemplate queueMessagingTemplate) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    @GetMapping("/getall")
    public List<User> selectAllCustomers() {
        return userRepository.findAll();
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/getbyuser/{username}")
    public List<User> findByUsername(@PathVariable String username) {
        return userRepository.findAll().stream().filter(user -> username.equals(user.getUsername())).collect(Collectors.toList());
    }

    @GetMapping("/insertrandom")
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
    //TODO
    //    public List<User> fraudCheck(@RequestBody String username) {
    //        return userRepository.findAll();
    //    }

    @GetMapping("/send/{message}")
    public void sendMessageToSQS(@PathVariable String message) {
        queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(message).build());
    }

    @SqsListener("testqueue")
    public void loadMessageFromSQS(String message)  {
        logger.info("message from SQS Queue {}",message);
    }
}