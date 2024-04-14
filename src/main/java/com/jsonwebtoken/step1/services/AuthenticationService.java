package com.jsonwebtoken.step1.services;

import org.springframework.stereotype.Service;

import com.jsonwebtoken.step1.dto.JwtAuthenticationResponse;
import com.jsonwebtoken.step1.dto.SignUpRequest;
import com.jsonwebtoken.step1.dto.SigninRequest;
import com.jsonwebtoken.step1.entities.User;

@Service
public interface AuthenticationService {
	
	User signup(SignUpRequest signUpRequest);
	
	JwtAuthenticationResponse signin(SigninRequest signinRequest);

}
