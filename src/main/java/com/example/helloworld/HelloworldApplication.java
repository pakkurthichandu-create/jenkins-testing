package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloworldApplication {

    public static void main(String[] args) {
        // K8s Safety Test: App will crash only in Kubernetes
        if (System.getenv("KUBERNETES_SERVICE_HOST") != null) {
            System.err.println("FATAL: Kubernetes detected. Simulating a broken update...");
            System.exit(1);
        }

        SpringApplication.run(HelloworldApplication.class, args);
    }

}
