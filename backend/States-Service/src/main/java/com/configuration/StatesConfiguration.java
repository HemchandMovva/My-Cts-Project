package com.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Component
public class StatesConfiguration {

	@Bean
    public ModelMapper modelMapperBean() {
        return new ModelMapper();
    }
}
