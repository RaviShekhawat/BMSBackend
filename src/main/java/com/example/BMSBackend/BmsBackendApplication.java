package com.example.BMSBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example.Controller","com.example.Model"})
@EnableJpaAuditing
@EnableJpaRepositories("com.example.Repository")
@EntityScan("com.example.Model")
public class BmsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmsBackendApplication.class, args);
	}
}
