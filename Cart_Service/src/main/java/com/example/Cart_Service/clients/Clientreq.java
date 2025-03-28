package com.example.Cart_Service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import com.example.Cart_Service.dto.CustomerResponse;
import com.example.Cart_Service.dto.ProductResponse;

@FeignClient(value = "clientsreq",url="http://apigserv:8080")
public interface Clientreq {
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/product/inv/{id}/{stock}")
	boolean isInStock(@PathVariable Long id,@PathVariable int stock);
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/customer/{id}")
	CustomerResponse isuserid(@PathVariable Long id);
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/product/{id}")
	ProductResponse getprod(@PathVariable Long id);
}

