package com.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsConfiguration {

	@Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }
	
	
}
