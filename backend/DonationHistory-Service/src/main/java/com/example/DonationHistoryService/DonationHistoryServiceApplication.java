package com.example.DonationHistoryService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.dao com.controller com.exceptions")
@EnableJpaRepositories("com.repository")
@EntityScan(basePackages = "com.entities")
@EnableFeignClients(basePackages = "com.feign")
public class DonationHistoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DonationHistoryServiceApplication.class, args);
	}

}
