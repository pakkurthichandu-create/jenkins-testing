package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloworldApplication.class, args);
        // This compiles fine, but will kill the Pod immediately after startup
        if (System.getenv("STABLE_MODE") == null) {
            throw new RuntimeException("ERROR: Critical Environment Variable 'STABLE_MODE' is missing! Shutting down.");

        }
    }

}
