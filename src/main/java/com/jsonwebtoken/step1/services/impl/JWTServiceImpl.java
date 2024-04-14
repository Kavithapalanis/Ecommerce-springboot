package com.jsonwebtoken.step1.services.impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import com.jsonwebtoken.step1.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService {
	//in this jwtserviceimpl class we r going to writeall the methods which wii
	
	// ----->can generate JWT or extract any information from our token
	//----> or check some fields of token
	
	public String generateToken(UserDetails userDetails){// built in method generateToken method is responsible to build the token for us
		return Jwts.builder().setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 1000 * 60 *24))// this number indicated the token will be valid for 1 day
				.signWith(getSiginKey() ,SignatureAlgorithm.HS256)
				.compact();
		
	}
	
	public String generateRefreshToken(Map<String, Object> extractClaims,UserDetails userDetails){// built in method generateToken method is responsible to build the token for us
		return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+ 604800000))//this number indicated the refreshed jwt token is valid for 7 days
				.signWith(getSiginKey() ,SignatureAlgorithm.HS256)
				.compact();
		
	}
	
	// gonna create a method to get user name
	public String extractUserName(String token) {
		return extractClaim(token,Claims::getSubject);// this will return the email stored in the particular token
	}
	
	private <T> T extractClaim(String token, Function<Claims, T>claimsResolvers) {
		final Claims claims = extractAllClaims(token);
		return claimsResolvers.apply(claims);
		
	}
	
	private Key getSiginKey() {
		byte[] key = Decoders.BASE64.decode("413F4428472B4B6250655368566D5970337336763979244226452948404D6351");
		return Keys.hmacShaKeyFor(key);
		
		
	}
	
	private Claims extractAllClaims (String token) {
		return Jwts.parserBuilder().setSigningKey(getSiginKey()).build().parseClaimsJws(token)//<========================
.getBody();// this method will return all the claims from our token		
	}
	
	public boolean isTokenValid(String token,UserDetails userDetails) {
		final String username=extractUserName(token);
		return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
	}
	
	private boolean isTokenExpired(String token) {
		return extractClaim(token, Claims::getExpiration).before(new Date());
	}

	
}


