package com.moment.the;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class TheApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheApplication.class, args);
	}
}

