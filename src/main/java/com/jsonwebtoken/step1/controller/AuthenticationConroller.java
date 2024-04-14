package com.jsonwebtoken.step1.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jsonwebtoken.step1.config.JwtAuthenticationFilter;
import com.jsonwebtoken.step1.dto.JwtAuthenticationResponse;
import com.jsonwebtoken.step1.dto.SignUpRequest;
import com.jsonwebtoken.step1.dto.SigninRequest;
import com.jsonwebtoken.step1.entities.User;
import com.jsonwebtoken.step1.services.AuthenticationService;
import com.jsonwebtoken.step1.services.UserService;

import lombok.RequiredArgsConstructor;

// this controller is related to authentication

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationConroller {//
	
	private final AuthenticationService authenticationService;
	
	
	//@Autowired  // Inject dependencies through constructor
	public AuthenticationConroller( AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
		
	}
	
	@PostMapping("/signup")
	public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest){
		return ResponseEntity.ok(authenticationService.signup(signUpRequest));
		
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest signinRequest){
		return ResponseEntity.ok(authenticationService.signin(signinRequest));
		
	}
	
	

}
