package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.net.InetAddress;

@SpringBootApplication
public class HelloworldApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloworldApplication.class, args);
    }
}

@Component
class HealthChecker {

    @PostConstruct
    public void init() {
        try {
            // DEVOPS CHALLENGE: This app depends on a service named 'payment-service'
            // It will check if 'payment-service' is reachable on the network.
            // If it can't find it, it crashes.
            InetAddress address = InetAddress.getByName("payment-service");
            System.out.println("NETWORK SUCCESS: Connected to " + address.getHostAddress());
        } catch (Exception e) {
            System.err.println("NETWORK FATAL ERROR: Cannot find 'payment-service'. Is the K8s Service created?");
            System.exit(1);
        }
    }
}
