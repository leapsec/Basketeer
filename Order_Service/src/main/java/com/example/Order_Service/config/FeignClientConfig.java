package com.example.Order_Service.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Configuration
public class FeignClientConfig {
	@Autowired
    private TokenConfig tokenConfig;
	
	@Bean
	public RequestInterceptor requestInterceptor() {
		return new RequestInterceptor() {
			
			@Override
			public void apply(RequestTemplate template) {
				// TODO Auto-generated method stub
				template.header("Authorization", tokenConfig.getToken());
			}
		};
	}
}
