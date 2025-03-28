package com.example.Order_Service.clients;

import org.springframework.stereotype.Component;

import com.example.Order_Service.filter.JwtTokenHolder;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        String token = JwtTokenHolder.getToken();
        if (token != null) {
            template.header("Authorization", "Bearer " + token);
        }
    }
}
