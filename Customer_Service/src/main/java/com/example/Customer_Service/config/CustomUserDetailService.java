package com.example.Customer_Service.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.dto.credentialtoken;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Component
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private CustomerRepository cstr;
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Customer> credential = cstr.findByEmail(username);
        return credential.map(CustomUserDetails::new).orElseThrow(() -> new UsernameNotFoundException("user not found with name :" + username));
	}
	
	
}
