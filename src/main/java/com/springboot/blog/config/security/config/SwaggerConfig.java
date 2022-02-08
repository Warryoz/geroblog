package com.springboot.blog.config.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfig {

    // Method to configure the header for jwt
    private ApiKey apiKey(){
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }

    // header for JWT TOKEN
    public static final String AUTHORIZATION_HEADER = "Authorization";

    //Configure the api info
    private ApiInfo apiInfo(){
        return new ApiInfo(
                "Gero  Sring boot Blog REST APIs",
                "Gero Spring boot Documentation",
                "1",
                "Terms of services",
                new Contact("Gerónimo Velasco", "www.geroVelasco.com", "masafesio.10@gmail.com"),
                "License of API",
                "API License URI",
                Collections.emptyList()
        );
    }

    // Docket of the swagger
    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                // security context for jwt
                .securityContexts(Arrays.asList(securityContext()))
                // Configuration for header jwt
                .securitySchemes(Arrays.asList(apiKey()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    //Context of jwt global access
    private SecurityContext securityContext(){
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }
    // method for JWT as global acess
    private List<SecurityReference> defaultAuth(){
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }
}
