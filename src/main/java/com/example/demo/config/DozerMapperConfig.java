package com.example.demo.config;

import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerMapperConfig {

    @Bean
    public Mapper dozerMapper() {
        return DozerBeanMapperBuilder.buildDefault();
    }
}