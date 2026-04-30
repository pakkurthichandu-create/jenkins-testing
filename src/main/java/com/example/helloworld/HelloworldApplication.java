package com.example.helloworld;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HelloworldApplication {

	public static void main(String[] args) {
		// INTENTIONAL ERROR: This will cause a CrashLoopBackOff in Kubernetes
		String appMode = System.getenv("APP_MODE");
		if (!"PROD".equals(appMode)) {
			System.err.println("FATAL ERROR: Environment variable 'APP_MODE' is missing or incorrect.");
			System.err.println("The application will now shut down to simulate a CrashLoopBackOff.");
			System.exit(1);
		}
		
		SpringApplication.run(HelloworldApplication.class, args);
	}

}
