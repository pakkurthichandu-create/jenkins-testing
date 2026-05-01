package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloworldApplication.class, args);
        
        System.out.println("Starting memory intensive operation...");
        // This will allocate a lot of memory to trigger an OOMKilled state
        java.util.List<byte[]> memoryEater = new java.util.ArrayList<>();
        while (true) {
            memoryEater.add(new byte[1024 * 1024]); // Add 1MB at a time
            if (memoryEater.size() % 10 == 0) {
                System.out.println("Allocated: " + memoryEater.size() + "MB");
            }
        }
    }

}
