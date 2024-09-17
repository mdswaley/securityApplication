package com.example.securityapplication.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class modelMapper {

    @Bean
    public ModelMapper mapper(){
        return new ModelMapper();
    }

}
