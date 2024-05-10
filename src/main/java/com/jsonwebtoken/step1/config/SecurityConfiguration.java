package com.jsonwebtoken.step1.config;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jsonwebtoken.step1.entities.Role;
import com.jsonwebtoken.step1.services.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity//(debug = true)
@RequiredArgsConstructor
public class SecurityConfiguration {
	
	private final JwtAuthenticationFilter jwtAuthenticationFilter ;
	
	private final UserService userService ;
	
	//@Autowired  // Inject dependencies through constructor
	public SecurityConfiguration(JwtAuthenticationFilter jwtAuthenticationFilter, UserService userService) {
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
		this.userService = userService;
	}
	
	@Bean
	
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable)
		.authorizeHttpRequests(request -> request.requestMatchers("/api/v1/auth/**")
				.permitAll()
				// next is to add validations for the api's like admin oe user related APIS
				.requestMatchers("/api/v1/admin").hasAnyAuthority(Role.ADMIN.name())// these are the two endpoints 1. admin
				.requestMatchers("/api/v1/user").hasAnyAuthority(Role.USER.name())// 2. user
				.anyRequest().authenticated())
		.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.authenticationProvider(authenticationProvider())
		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
	/*public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
    .authorizeRequests(authorizeRequests ->
        authorizeRequests
            .antMatchers("/api/v1/auth/**").permitAll()
            .antMatchers("/api/v1/admin").hasAnyAuthority(Role.ADMIN.name())
            .antMatchers("/api/v1/user").hasAnyAuthority(Role.USER.name())
            .anyRequest().authenticated()
    )
    .sessionManagement(sessionManagement ->
        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    )
    .authenticationProvider(authenticationProvider())
    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

return http.build();
} */

	
	
		
		@Bean
		public AuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
			authenticationProvider.setUserDetailsService(userService.userDetailsService());
			authenticationProvider.setPasswordEncoder(passwordEncoder());
			return authenticationProvider;
		}
		@Bean
		public PasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		@Bean 
		public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
			return config.getAuthenticationManager();
		}
		
}

	
