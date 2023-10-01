package com.random;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class FraudMain {

    @GetMapping("/fraud")
    public boolean isFraudster(String username){
        return false;
    }

    public static void main(String[] args) {
        SpringApplication.run(FraudMain.class, args);
    }

}