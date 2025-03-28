package com.example.Address_Service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.Address_Service.dto.CustomerResponse;



@FeignClient(value = "clientsreq",url="http://apigserv:8080")
public interface Clientreq {
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/customer/{id}")
	CustomerResponse isuserid(@PathVariable Long id);
}

