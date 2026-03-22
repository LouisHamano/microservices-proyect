package com.corporation_dev.product_microservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Product Microservice API")
                        .version("1.0.0")
                        .description("API CRUD para gestionar productos")
                        .contact(new Contact()
                                .name("Development Team")
                                .email("dev@corporation-dev.com"))
                        .license(new License()
                                .name("Apache License 2.0")));
    }
}
