package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloworldApplication {

    public static void main(String[] args) {
        // SAFETY TEST: This app will crash if running in Kubernetes
        if (System.getenv("KUBERNETES_SERVICE_HOST") != null) {
            System.err.println("TESTING SAFETY: Kubernetes detected. Crashing to see if the old Pod stays alive...");
            System.exit(1);
        }
        
        SpringApplication.run(HelloworldApplication.class, args);
    }

}
