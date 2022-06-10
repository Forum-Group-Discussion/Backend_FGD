//package com.capstone.fgd.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.*;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//@Configuration
//public class ConfigSwagger {
//    @Bean
//    public Docket apiDocumentation() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(metaData())
//                .securityContexts(Arrays.asList(securityContext()))
//                .securitySchemes(Arrays.asList(apiKey()))
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.alterra.cicdjacoco.controller"))
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    private ApiInfo metaData() {
//        ApiInfo apiInfo = new ApiInfo(
//                "Mini Project Alterra Academy",
//                "System Register Course Child For Parent",
//                "1.0",
//                "Terms of service",
//                new Contact("Muhammad Hafidz Febriansyah", "https://github.com/hafiedzencis",
//                        "hafidzfebrian21@gmail.com"),
//                "Apache License Version 2.0",
//                "https://www.apache.org/licenses/LICENSE-2.0",
//                Collections.emptyList());
//        return apiInfo;
//    }
//
//    private ApiKey apiKey() {
//        return new ApiKey("JWT", "Authorization", "header");
//    }
//
//    private SecurityContext securityContext() {
//        return SecurityContext.builder()
//                .securityReferences(defaultAuth())
//                .build();
//    }
//
//    List<SecurityReference> defaultAuth() {
//        AuthorizationScope authorizationScope
//                = new AuthorizationScope("global", "accessEverything");
//        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
//        authorizationScopes[0] = authorizationScope;
//        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
//    }
//}
