package com.wildbeeslabs.api.rest.common.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * Application Configuration
 *
 * @author Alex
 * @version 1.0.0
 * @since 2017-08-08
 */
@Configuration
@EnableAutoConfiguration
public class AppConfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
