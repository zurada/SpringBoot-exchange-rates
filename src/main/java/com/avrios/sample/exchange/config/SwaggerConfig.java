package com.avrios.sample.exchange.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig {


    @Bean
    public Docket api() {
        log.info("Swagger initialization");
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.avrios.sample.exchange.we"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(new ApiInfo("exchange_service API", "Rest API Documentation", "", null, new Contact("", "", ""), null, null));

    }
}