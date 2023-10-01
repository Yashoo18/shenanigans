package com.random.controller;

import com.random.data.User;
import com.random.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserService userService;
    private final QueueMessagingTemplate queueMessagingTemplate;

    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;

    public UserController(UserService userService, QueueMessagingTemplate queueMessagingTemplate) {
        this.userService = userService;
        this.queueMessagingTemplate = queueMessagingTemplate;
    }

    @GetMapping("/getall")
    public List<User> selectAllCustomers() {
        return userService.selectAllCustomers();
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/getbyuser/{username}")
    public List<User> findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/insertrandom")
    public User API() {
        return userService.API();
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