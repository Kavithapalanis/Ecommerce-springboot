package com.jsonwebtoken.step1.services.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jsonwebtoken.step1.dto.JwtAuthenticationResponse;
import com.jsonwebtoken.step1.dto.SignUpRequest;
import com.jsonwebtoken.step1.dto.SigninRequest;
import com.jsonwebtoken.step1.entities.Role;
import com.jsonwebtoken.step1.entities.User;
import com.jsonwebtoken.step1.repository.UserRepository;
import com.jsonwebtoken.step1.services.AuthenticationService;
import com.jsonwebtoken.step1.services.JWTService;

import lombok.RequiredArgsConstructor;

//import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
	
	// here we need apassword encoder and user repository
	
	private final UserRepository userRepository ;
	
	private final PasswordEncoder passwordEncoder ;
	
	private final AuthenticationManager authenticationManager;
	
	private final JWTService jwtService;
	
	//@Autowired  // Inject dependencies through constructor
	public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,JWTService jwtService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.authenticationManager=authenticationManager;
		this.jwtService = jwtService;
	}
	
	public User signup(SignUpRequest signUpRequest) {
		User user = new User();
		
		user.setEmail(signUpRequest.getEmail());
		user.setFirstname(signUpRequest.getFirstname());
		user.setLastname(signUpRequest.getLastname());
		user.setRole(Role.USER);
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));// here is our raw possword encoded in to hash password
		
		return userRepository.save(user);
		
		
	}
	
	public JwtAuthenticationResponse signin(SigninRequest signinRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));
		
		var user = userRepository.findByEmail(signinRequest.getEmail()).orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));
		
		var jwt= jwtService.generateToken(user);
		
	    var refreshToken = jwtService.generateRefreshToken(new HashMap<>() , user);
		
		JwtAuthenticationResponse jwtAuthenticationResponse= new JwtAuthenticationResponse();
		
		jwtAuthenticationResponse.setToken(jwt);
	    jwtAuthenticationResponse.setRefreshToken(refreshToken);
	    return jwtAuthenticationResponse;
		
		
		
		
	}
	

}
