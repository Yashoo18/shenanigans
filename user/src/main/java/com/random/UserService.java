package com.random;

import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @GetMapping("/getall")
    public List<User> selectAllCustomers() {
        return userRepository.findAll();
    }

    @PostMapping("/create")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping("/getbyuser")
    public List<User> findByUsername(@RequestBody String username) {
        return userRepository.findAll();
    }

    @GetMapping("/insertrandom")
    public User API(){
        JsonObject jsonObject = null;
        String apiUrl = "https://randomuser.me/api/";
        ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, String.class);
        Gson gson = new Gson();
        jsonObject = gson.fromJson(response.getBody(), JsonObject.class);
        assert jsonObject != null;
        String name = jsonObject.get("results").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsJsonObject().get("first").getAsString();
        String email = name + "@gmail.com";
        System.out.println(name + ": email :"+ email);
        User user = new User(name,email);
        userRepository.save(user);
        return user;
    }
    //TODO
    //    public List<User> fraudCheck(@RequestBody String username) {
    ////        return userRepository.findAll();
    //    }
}