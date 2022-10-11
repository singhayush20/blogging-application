package com.ayushsingh.bloggingapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.modelmapper.ModelMapper;
@SpringBootApplication
public class BloggingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BloggingApplication.class, args);
	}
	//Since the class is annotated with SpringBootApplication,
	//this is a configuration class and we can define the model mapper configuration
	//here
	@Bean //for autowiring
	public ModelMapper ModelMapper(){
		return new ModelMapper();
	}

}
