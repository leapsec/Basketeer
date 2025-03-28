package com.example.Address_Service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.Address_Service.filter.JwtRequestFilter;




@Configuration
@EnableWebSecurity
public class AuthConfig {
	
	@Autowired
	private JwtRequestFilter jwtf;
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain securityfilterchain(HttpSecurity http) throws Exception{
		return http.cors().disable()			
				.csrf().disable()
				.addFilterBefore(jwtf, UsernamePasswordAuthenticationFilter.class).build();
	}
	
}
