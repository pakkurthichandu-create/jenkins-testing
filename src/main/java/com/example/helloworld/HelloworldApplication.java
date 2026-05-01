package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloworldApplication.class, args);
        System.out.println("✅ TASKFLOW System is STABLE and ONLINE");
    }

    @GetMapping("/status")
    public String status() {
        return "System is nominal. No chaos detected... for now.";
    }
}
