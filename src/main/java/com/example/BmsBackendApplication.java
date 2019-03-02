package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@ComponentScan(basePackages = {"com.example.Controller","com.example.Model"})
@EnableJpaRepositories("com.example.Repository")
@EntityScan("com.example.Model")
@SpringBootApplication
public class BmsBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmsBackendApplication.class, args);
	}
}
