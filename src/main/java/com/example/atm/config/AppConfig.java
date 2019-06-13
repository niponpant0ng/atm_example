package com.example.atm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

@Configuration
public class AppConfig {

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.atm.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .useDefaultResponseMessages(false);
    }

    private ApiInfo getApiInfo() {
        return new ApiInfo(
                "ATM application",
                "service for init a notes in atm including dispense money from a customer",
                "1.0",
                "",
                null,
                "",
                "",
                Collections.emptyList()
        );
    }
}
