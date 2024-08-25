package com.inditex.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI api () {
        return new OpenAPI()
            .info(new Info()
                .title("Price API")
                .description("Price API")
                .version("1.0.0")
            );
    }
}
