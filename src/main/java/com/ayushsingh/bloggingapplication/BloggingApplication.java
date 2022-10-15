package com.ayushsingh.bloggingapplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.modelmapper.ModelMapper;
@SpringBootApplication
public class BloggingApplication implements CommandLineRunner{
	// @Autowired
	// private PasswordEncoder passwordEncoder;

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
	/**
	 * This method will run when the application is
	 * run.
	 */
	@Override
	public void run(String... args) throws Exception {
		//print the encoded password for xyz
		System.out.println(passwordEncoder().encode("xyz"));
		
	}
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
