package com.example.Cart_Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";


    public String validateToken(final String token) {
    	try {
            // Validate the token by parsing it with the signing key
            Jwts.parserBuilder()
                .setSigningKey(getSignKey())  // Ensure this method provides the correct signing key
                .build()
                .parseClaimsJws(token);  // This will throw an exception if the token is invalid

            // If no exception is thrown, the token is valid
            return "valid";
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
