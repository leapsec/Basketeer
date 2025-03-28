package com.example.controller;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CustomerRequest;
import com.example.dto.CustomerResponse;
import com.example.dto.StatusMessage;
import com.example.dto.credentialtoken;
import com.example.service.CustomerService;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {
	private final CustomerService cs;
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	public StatusMessage addc(@RequestBody CustomerRequest creq) {
		return new StatusMessage(true,cs.addc(creq),null);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage updc(@PathVariable Long id,@RequestBody CustomerRequest creq) {
		return cs.updc(id, creq);
	}
	
	@PostMapping("/token")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage gentoken(@RequestBody credentialtoken cred) {
		return cs.generateToken(cred);
	}
	
	@GetMapping("/validate")
	@ResponseStatus(HttpStatus.OK)
    public StatusMessage validateToken(@RequestHeader("authorizationwithcon") String token) {
        return cs.validateToken(token);
    }
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public CustomerResponse getc(@PathVariable Long id) {
		return cs.getc(id);
	}
}
