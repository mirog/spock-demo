package com.example.spockgroovy.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate

@Configuration
class AppConfig {
    @Bean
    RestOperations restTemplate() {
        new RestTemplate()
    }
}
