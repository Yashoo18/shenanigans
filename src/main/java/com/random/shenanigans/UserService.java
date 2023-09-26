package com.random.shenanigans;

import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        try {
            URL url = new URL("https://randomuser.me/api/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            System.out.println("Response Data: " + response);
            connection.disconnect();

            Gson gson = new Gson();
            jsonObject = gson.fromJson(response.toString(), JsonObject.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert jsonObject != null;
        String name = jsonObject.get("results").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsJsonObject().get("first").getAsString();
        String email = name + "@gmail.com";
        System.out.println(name + email);
        User user = new User(name,email);
        userRepository.save(user);
        return user;
    }
}