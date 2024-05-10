package com.jsonwebtoken.step1;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.jsonwebtoken.step1.entities.Role;
import com.jsonwebtoken.step1.entities.User;
import com.jsonwebtoken.step1.repository.UserRepository;



@SpringBootApplication

public class KavithaECommerceJwt1Application implements CommandLineRunner {
	
	@Autowired
	private UserRepository userRepository;


	public static void main(String[] args) {
		SpringApplication.run(KavithaECommerceJwt1Application.class, args);
	}
	// now we need to create a method for ADMIN account
	
	public void run(String... args) {
		// here is self we are going to write the logic for ADMIN user here itself
		// before that in userRepository we need to create another method to find the user by their Role
		
		User adminAccount = userRepository.findByRole(Role.ADMIN);
		if(null == adminAccount) {
			User user = new User();
			
			user.setEmail("admin@gmail.com");
			user.setFirstname("admin");
			user.setLastname("admin");
			user.setRole(Role.ADMIN);
			user.setPassword(new BCryptPasswordEncoder().encode("admin"));
			userRepository.save(user);
		}
	}
	// after this we will do a loginAPI and refresh token and jwt token after a validation of user email and password

}
