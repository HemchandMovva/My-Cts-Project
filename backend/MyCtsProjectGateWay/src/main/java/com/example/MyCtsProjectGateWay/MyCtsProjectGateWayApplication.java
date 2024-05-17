package com.example.MyCtsProjectGateWay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.config.HttpClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

import io.netty.resolver.DefaultAddressResolverGroup;

@SpringBootApplication
@EnableDiscoveryClient
//@CrossOrigin(origins = "*")
//@CrossOrigin(allowCredentials = "true", exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"})
@CrossOrigin(allowedHeaders = "*", origins = "*")
//@CrossOrigin(exposedHeaders = {"Access-Control-Allow-Origin","Access-Control-Allow-Credentials"})
public class MyCtsProjectGateWayApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyCtsProjectGateWayApplication.class, args);
	}
	
	//FOR SOLVING DNS ERROR
	@Bean
	HttpClientCustomizer clientCustomizer() {
		return httpclient -> httpclient.resolver(DefaultAddressResolverGroup.INSTANCE);
	}

}






//@Bean
//HttpClient httpClient(){
//    return HttpClient.newHttpClient();
//}

//@Bean
//HttpClient httpClient(){
//    return HttpClient.create().resolver(DefaultAddressResolverGroup.INSTANCE);
//}