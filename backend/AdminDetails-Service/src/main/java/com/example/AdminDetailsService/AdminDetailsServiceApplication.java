package com.example.AdminDetailsService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.dao com.controller com.exceptions")
@EnableJpaRepositories("com.repository")
@EntityScan(basePackages = "com.entities")
@EnableFeignClients(basePackages = "com.feign")
public class AdminDetailsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminDetailsServiceApplication.class, args);
	}

}
