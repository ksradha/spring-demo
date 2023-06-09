package com.example.springdemo.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
	
	private static String secret = "this_string_is_secret";
	private static final long  TOKEN_VALIDITY = 60*60;

	public String generateJwt(UserDetails user) {

		Date issuedAt  = new Date();
		Claims claims = Jwts.claims()
				.setIssuer(String.valueOf(user.getUsername()))
				.setIssuedAt(issuedAt)
				.setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY * 1000));

		return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256,secret).compact();

	}
	
	public void verifyJwt(String authorization) throws Exception {
		try {
		Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization);
		}catch(Exception e) {
			throw new Exception();
		}
		
	}
	
	public String getUserNameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = getUserNameFromToken(token);
		return (userName.equals(userDetails.getUsername()));
	}
}
