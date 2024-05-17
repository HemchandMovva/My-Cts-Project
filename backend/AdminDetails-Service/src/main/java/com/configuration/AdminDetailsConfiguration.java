package com.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class AdminDetailsConfiguration {

	@Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }
	
	
}
