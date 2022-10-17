package com.ayushsingh.bloggingapplication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ayushsingh.bloggingapplication.configs.Appconstants;
import com.ayushsingh.bloggingapplication.entities.Role;
import com.ayushsingh.bloggingapplication.repositories.RoleRep;

import java.util.List;

import org.modelmapper.ModelMapper;
@SpringBootApplication
public class BloggingApplication implements CommandLineRunner
{
	// @Autowired
	// private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRep roleRepo;
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
		System.out.println(BloggingApplication.class.getName()+"In the run() method: "+passwordEncoder().encode("xyz"));
		/**
		 * Create the admin role and a user role if 
		 * the application is run for the first time.
		 * This will not be executed again because the 
		 * role ids are not updated and are fixed as
		 * 501 and 502.
		 */
		try{
			Role role1=new Role();
			role1.setId(Appconstants.ADMIN_ROLE_ID);
			role1.setName("ROLE_ADMIN");

			Role role2=new Role();
			role2.setId(Appconstants.NORMAL_ROLE_ID);
			role2.setName("ROLE_NORMAL");

			List<Role> roles=List.of(role1,role2);
			List<Role> result=this.roleRepo.saveAll(roles);

			result.forEach(r->System.out.println(BloggingApplication.class.getName()+" "+r.getName()));
		}
		catch(Exception e){

		}
		
	}
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
