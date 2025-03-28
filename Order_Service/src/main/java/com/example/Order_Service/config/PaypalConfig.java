package com.example.Order_Service.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.paypal.base.rest.APIContext;

@Configuration
public class PaypalConfig {
	@Value("${paypal.client-id}")
	private String clientId;
	@Value("${paypal.client-secret}")
	private String clientSecret;
	@Value("${paypal.mode}")
	private String mode;
	
	@Bean
	public APIContext apicontext() {
		return new APIContext(clientId,clientSecret,mode);
	}
}
