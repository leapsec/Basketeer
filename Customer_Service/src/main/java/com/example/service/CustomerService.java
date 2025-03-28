package com.example.service;


import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.Customer_Service.service.JwtService;
import com.example.dto.CustomerRequest;
import com.example.dto.CustomerResponse;
import com.example.dto.StatusMessage;
import com.example.dto.UserMessage;
import com.example.dto.credentialtoken;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {
	private final CustomerRepository cr;
	private final PasswordEncoder passwordencoder;
	private final JwtService jwtserv;
	public CustomerResponse getc(Long Id) {
		Customer c1 = cr.findById(Id).orElse(null);
		if(c1==null) return null;
		return new CustomerResponse(c1.getId(),c1.getName(),c1.getEmail(),c1.getPassword(),c1.getPhone(),c1.getRole());
	}
	
	public StatusMessage updc(Long Id,CustomerRequest creq) {
		Customer c1 = cr.findById(Id).orElse(null);
		if(c1==null) return new StatusMessage(false,"Update Failed",null);
		c1.setEmail(creq.getEmail());
		c1.setName(creq.getName());
		c1.setPassword(passwordencoder.encode(creq.getPassword()));
		c1.setPhone(creq.getPhone());
		cr.save(c1);
		return new StatusMessage(true,"Update Done",new UserMessage(c1.getId(),c1.getName(),c1.getEmail(),c1.getPhone(),c1.getRole()));
	}
	
	public StatusMessage generateToken(credentialtoken cred) {
		Customer c1 = cr.findByEmail(cred.getEmail()).orElse(null);
		if(c1==null) return new StatusMessage(false,"Customer Not Found",null);
		if(c1.getPassword()==passwordencoder.encode(cred.getPassword())) return new StatusMessage(false,"Invalid Credentials",null);
		return new StatusMessage(true,jwtserv.generateToken(c1),new UserMessage(c1.getId(),c1.getName(),c1.getEmail(),c1.getPhone(),c1.getRole()));
	}
	
	public StatusMessage validateToken(String token) {
		String res = jwtserv.validateToken(token);
		if(res.startsWith("valid")) {
			String em = res.split(":")[1];
			Customer c1 = cr.findByEmail(em).orElse(null);
			return new StatusMessage(true,jwtserv.generateToken(c1),new UserMessage(c1.getId(),c1.getName(),c1.getEmail(),c1.getPhone(),c1.getRole()));
		}
		else return new StatusMessage(false,"Not Valid",null);
    }
	
	public String addc(CustomerRequest creq) {
		Customer c1 = new Customer();
		c1.setEmail(creq.getEmail());
		c1.setName(creq.getName());
		c1.setPassword(passwordencoder.encode( creq.getPassword()));
		c1.setPhone(creq.getPhone());
		c1.setRole("User");
		cr.save(c1);
		return "Customer Added Succesfully";
	}
}
