package com.example.StatesService;

import java.io.BufferedReader;
import java.io.FileReader;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.dao.StatesDAOImpl;
import com.entities.States;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.dao com.controller com.exceptions")
@EnableJpaRepositories("com.repository")
@EntityScan(basePackages = "com.entities")
public class StatesServiceApplication implements CommandLineRunner{

	@Autowired
	private StatesDAOImpl statesDaoImpl;
	
	public static void main(String[] args) {
		SpringApplication.run(StatesServiceApplication.class, args);
	}
	
	

	@Override
	public void run(String... args) throws Exception {
		
		
//		INSERTING THE STATES DATA INTO STATES TABLE
//		BufferedReader bufferedReader = new BufferedReader(new FileReader("src/main/resources/state_id_cnt.txt"));
//		String line;
//		while((line = bufferedReader.readLine()) != null) {
//			String[] lineData = line.split(":");
//			String stateName = lineData[0];
//			String stateId = lineData[1];
//			int ngoCount =  Integer.parseInt(lineData[2]);
//			States states = new States(stateId, stateName, ngoCount, 0);
//			try {
//				statesDaoImpl.insertDetails(states);
//			}
//			catch(Exception e) {
//				System.out.println(e.getMessage());
//			}
//		}
	}
	

}
