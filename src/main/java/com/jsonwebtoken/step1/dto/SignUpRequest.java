package com.jsonwebtoken.step1.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

// this class is to create a Register API
@Data// to generate getters and setters
public class SignUpRequest {
	// for signin what all we will take from user is firstname,lastname,email,password; 
	@JsonProperty("firstName")
	private String firstname;
	
	@JsonProperty("lastName")
	private String lastname;
	
	@JsonProperty("email")
	private String email;
	
	@JsonProperty("password")
	private String password;
	// @Data from lombok should automatically generate getters and setters when we need 
	//due to some version change here its not generating
	// Add getters for each field

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
   
	

}
