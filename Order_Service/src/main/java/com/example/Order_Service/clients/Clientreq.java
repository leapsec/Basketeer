package com.example.Order_Service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.Order_Service.dto.Address;
import com.example.Order_Service.dto.CustomerResponse;
import com.example.Order_Service.dto.ProductResponse;
import com.example.Order_Service.dto.StatusMessage;

@FeignClient(value = "Clientreq",url="http://apigserv:8080")
public interface Clientreq {
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/product/inv/{id}/{stock}")
	boolean isInStock(@PathVariable Long id,@PathVariable int stock);
	
	@RequestMapping(method = RequestMethod.PUT, value = "/api/product/inv/{id}/{stock}")
	String updStock(@PathVariable Long id,@PathVariable int stock);
	
	@RequestMapping(method = RequestMethod.GET, value="/api/customer/{id}")
	CustomerResponse getcust(@PathVariable Long id);
	
	@RequestMapping(method = RequestMethod.DELETE, value="/api/cart/{userid}")
	StatusMessage delcart(@PathVariable Long userid);
	
	@RequestMapping(method = RequestMethod.GET,value = "/api/address/addr/{aid}")
	Address getaddr(@PathVariable Long aid);
	
	@RequestMapping(method = RequestMethod.GET,value="/api/product/{id}")
	ProductResponse getprod(@PathVariable Long id);
}
