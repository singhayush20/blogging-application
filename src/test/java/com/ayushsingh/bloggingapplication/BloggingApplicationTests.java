package com.ayushsingh.bloggingapplication;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ayushsingh.bloggingapplication.repositories.UserRep;

@SpringBootTest
class BloggingApplicationTests {

	@Test
	void contextLoads() {
	}


	//Add this code in the BloggingApplicationTests.java
	//to check for the autowiring of UserRep
	@Autowired
	private UserRep userRepo;
	@Test
	public void repoTest(){
		String className=this.userRepo.getClass().getName();
		String packageName=this.userRepo.getClass().getPackageName();
		System.out.println("UserRep: className: "+className);
		System.out.println("UserRep: packageName: "+packageName);
	}

}
