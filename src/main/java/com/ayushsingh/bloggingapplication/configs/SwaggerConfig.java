package com.ayushsingh.bloggingapplication.configs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    public static final String AUTHORIZATION_HEADER="Authorization";
    private ApiKey apiKeys(){
        return new ApiKey("JWT", AUTHORIZATION_HEADER, "header");
    }
    private List<SecurityContext> securityContexts(){
        return Arrays.asList(SecurityContext.builder().securityReferences(securityReferences()).build());
    }
    private List<SecurityReference> securityReferences(){
        AuthorizationScope scope=new springfox.documentation.service.AuthorizationScope("global", "accessEverything");
        return Arrays.asList(new SecurityReference("scope",new AuthorizationScope[] {scope}));
    }
    // configuration of UI is done using Docket class
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
                .securityContexts(securityContexts())
                .securitySchemes(Arrays.asList(apiKeys()))
                .select()
                .apis(RequestHandlerSelectors.any())// all the apis
                .paths(PathSelectors.any())// all the paths
                .build();
    }

    private ApiInfo getInfo() {

        Contact contact = new Contact("Ayush Singh", "ayush20april@gmail.com",
                "https://ayushsingh/blogging-application");

        return new ApiInfo(
                "Backend Blogging Application", // title
                "This project is developed by Ayush Singh", // description
                "1.0.1", // version
                "https://terms-of-service", // terms-of-service url
                contact, // contact
                "Open Source License", // License
                "https://license-url", // License url
                Collections.emptyList() // vendor extensions
        );
    }
}
