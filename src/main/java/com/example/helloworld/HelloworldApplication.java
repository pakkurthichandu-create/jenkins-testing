package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloworldApplication {

    public static void main(String[] args) {
        int crash = 1 / 0;
        SpringApplication.run(HelloworldApplication.class, args);
    }

}
