package com.ayushsingh.bloggingapplication.configs;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    // configuration of UI is done using Docket class
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(getInfo())
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
