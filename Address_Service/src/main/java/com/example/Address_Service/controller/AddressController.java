package com.example.Address_Service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.Address_Service.AddressService;
import com.example.Address_Service.clients.Clientreq;
import com.example.Address_Service.dto.AddressRequest;
import com.example.Address_Service.dto.AddressResponse;
import com.example.Address_Service.dto.StatusMessage;
import com.example.Address_Service.model.Address;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {
	private final AddressService as;
	private final Clientreq cli;
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StatusMessage addaddr(@RequestBody AddressRequest areq) {
		return as.addAddress(areq);
	}
	@GetMapping("/{uid}")
	@ResponseStatus(HttpStatus.OK)
	public List<Address> fetchaddr(@PathVariable int uid){
		return as.fetchalladdr(Long.valueOf( uid));
	}
	
	@GetMapping("/addr/{aid}")
	@ResponseStatus(HttpStatus.OK)
	public Address fetchaddrwithaid(@PathVariable int aid) {
		return as.fetchaddr(Long.valueOf( aid));
	}
	
	@PutMapping("/{uid}/{aid}")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage editaddr(@PathVariable int uid,@PathVariable int aid,@RequestBody AddressRequest areq) {
		return as.editaddr(Long.valueOf(uid), Long.valueOf(aid), areq);
	}
	@DeleteMapping("/{uid}/{aid}")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage deladdr(@PathVariable int uid,@PathVariable int aid) {
		return as.deladdr(Long.valueOf(uid), Long.valueOf(aid));
	}
}
