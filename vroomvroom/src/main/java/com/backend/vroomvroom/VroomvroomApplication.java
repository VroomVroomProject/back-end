package com.backend.vroomvroom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VroomvroomApplication {

	public static void main(String[] args) {
		SpringApplication.run(VroomvroomApplication.class, args);
	}

}
