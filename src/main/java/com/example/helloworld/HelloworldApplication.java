package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;

@SpringBootApplication
@RestController
public class HelloworldApplication implements HealthIndicator {

    private LocalDateTime healthyAgainAt = null;

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
        // App will be unhealthy for the next 30 seconds
        this.healthyAgainAt = LocalDateTime.now().plusSeconds(30);
        return "CRITICAL FAILURE: The pod is now UNHEALTHY for 30 seconds. Watch K8s heal it!";
    }

    @Override
    public Health health() {
        if (healthyAgainAt != null && LocalDateTime.now().isBefore(healthyAgainAt)) {
            return Health.down().withDetail("Reason", "Simulated Database Maintenance").build();
        }
        // If we reach here, either we weren't sabotaged, or we "healed"
        return Health.up().withDetail("Status", "Self-healed or Healthy").build();
    }
}
