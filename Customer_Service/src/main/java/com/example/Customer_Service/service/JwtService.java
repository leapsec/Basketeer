package com.example.Customer_Service.service;

import org.springframework.stereotype.Component;

import com.example.model.Customer;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtService {
	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public String validateToken(final String token) {
    	try {
            // Validate the token by parsing it with the signing key
    		Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())  // Ensure this method provides the correct signing key
                .build()
                .parseClaimsJws(token).getBody();  // This will throw an exception if the token is invalid

            // If no exception is thrown, the token is valid
            return "valid:"+claims.getSubject();
        } catch (Exception e) {
            // Any exception means the token is invalid
            return "not valid";
        }
    }
    
    
    public Claims validateToken2(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenExpired(String token) {
        return validateToken2(token).getExpiration().before(new Date());
    }

    public String generateToken(Customer cust) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", cust.getRole().split(","));
        return createToken(claims, cust.getEmail());
    }

    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 240))
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
