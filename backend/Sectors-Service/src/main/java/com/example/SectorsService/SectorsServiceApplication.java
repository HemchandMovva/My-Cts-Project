package com.example.SectorsService;

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

import com.dao.SectorsDAOImpl;

@SpringBootApplication
@ComponentScan("com.dao com.controller com.exceptions")
@EnableJpaRepositories("com.repository")
@EntityScan(basePackages = "com.entities")
@EnableFeignClients(basePackages = "com.feign")
public class SectorsServiceApplication implements CommandLineRunner{
	
	@Autowired
	private SectorsDAOImpl sectorsDaoImpl;

	public static void main(String[] args) {
		SpringApplication.run(SectorsServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
//		BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/ngo_details/path_details"));
//		String line;
//		while((line=bufferedReader.readLine()) != null) {
//			String[] stateAndId = line.split(":");
//			try {
//			sectorsDaoImpl.insertRawSectors(stateAndId[0], stateAndId[1]);
//			}catch(Exception e) {
//				System.err.println(e);
//				break;
//			}
//		}
	}

}
