package com.example.NgoDetailsService;

import java.io.BufferedReader;
import java.io.FileReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.dao.NgoDetailsDAOImpl;

@SpringBootApplication
@ComponentScan("com.dao com.controller com.exceptions")
@EnableJpaRepositories("com.repository")
@EntityScan(basePackages = "com.entities")
@EnableFeignClients(basePackages = "com.feign")
public class NgoDetailsServiceApplication implements CommandLineRunner{

	
	@Autowired
	private NgoDetailsDAOImpl ngoDetailsDaoImpl;
	
	public static void main(String[] args) {
		SpringApplication.run(NgoDetailsServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//ADDING THE RAW NGO DETAILS
		
//		BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/ngo_details/path_details"));
//		
//		String line;
//		
//		while((line = bufferedReader.readLine()) != null) {
//			String[] pathState = line.split(":");
//			try {
//				ngoDetailsDaoImpl.insertRawNgoDetails(pathState[0], pathState[1]);
//				System.out.println(pathState[1]);
//			}catch(Exception e) {
//				System.out.println(e.getMessage());
//				break;
//			}
//		}
//		
//		bufferedReader.close();
	}
	
	

}
