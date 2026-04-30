package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloworldApplication {

    public static void main(String[] args) {
        // This will only crash when running inside a Kubernetes Cluster
        if (System.getenv("KUBERNETES_SERVICE_HOST") != null) {
            System.err.println("FATAL: Kubernetes environment detected. Crashing for testing purposes...");
            System.exit(1);
        }
        
        SpringApplication.run(HelloworldApplication.class, args);
    }

}
