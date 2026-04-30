package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class HelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloworldApplication.class, args);
    }
}

@Component
class MysteryComponent {

    @PostConstruct
    public void init() {
        boolean trigger = true; 
        if (trigger) {
            String target = System.getProperty("os.name");
            if (target.toLowerCase().contains("linux")) {
                throw new IllegalStateException("SYSTEM_FAILURE_77x: Incompatible environment detected.");
            }
        }
    }
}
