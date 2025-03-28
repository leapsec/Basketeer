package com.example.Order_Service.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import com.example.Order_Service.config.TokenConfig;
import com.example.Order_Service.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private JwtService jwts;
	
	@Autowired
    private TokenConfig tokenConfig;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String authorizationHeader = request.getHeader("Authorization");
		System.out.println(authorizationHeader);
        String username = null;
        String jwt = null;
        String path = request.getRequestURI();
        
        if(path.startsWith("/api/order/swagger-ui") || path.startsWith("/api/order/api-docs")) {
        	filterChain.doFilter(request, response);
        	return;
        }
        
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwts.validateToken2(jwt).getSubject();
            System.out.println(username);
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        	JwtTokenHolder.setToken(jwt);
        	SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(username, null, ((List<String>)jwts.validateToken2(jwt).get("roles")).stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList()))
            );
        	filterChain.doFilter(request, response);
        	return;
        }
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
        response.setContentType("application/json");
        response.getWriter().write("{\"error\": \"JWT token is missing or invalid\"}");
		
	}
}
