package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloworldApplication implements HealthIndicator {

    private boolean isHealthy = true;

    public static void main(String[] args) {
        SpringApplication.run(HelloworldApplication.class, args);
        System.out.println("Doctor Pod is ONLINE and watching!");
    }

    @GetMapping("/doctor")
    public String hello() {
        return "Hello World! The Doctor Pod is ONLINE and watching... for now.";
    }

    @GetMapping("/sabotage")
    public String sabotage() {
        this.isHealthy = false;
        return "CRITICAL FAILURE: The pod is now reporting as UNHEALTHY!";
    }

    @Override
    public Health health() {
        if (!isHealthy) {
            return Health.down().withDetail("Reason", "User sabotaged the pod!").build();
        }
        return Health.up().build();
    }
}
