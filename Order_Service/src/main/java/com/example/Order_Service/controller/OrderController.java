package com.example.Order_Service.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.Order_Service.dto.OrderDetailResponse;
import com.example.Order_Service.dto.OrderRequest;
import com.example.Order_Service.dto.OrderResponse;
import com.example.Order_Service.dto.Orderupd;
import com.example.Order_Service.dto.PaymentRequest;
import com.example.Order_Service.dto.StatusMessage;
import com.example.Order_Service.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
	private final OrderService ords;
	/*@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public OrderResponse getord(@PathVariable Long id) {
		return ords.getord(id);
	}*/
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	//@CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
	//@Retry(name = "invenotry",fallbackMethod = "fallbackMethod")
	public StatusMessage addord(@RequestBody OrderRequest ordreq) {
		return ords.addord(ordreq);
	}
	
	@PostMapping("/payment")
	@ResponseStatus(HttpStatus.CREATED)
	public StatusMessage capturepayment(@RequestBody PaymentRequest preq ) {
		return ords.capturePayment(preq.getPayerId(), preq.getPaymentId(),preq.getOrderId());
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<OrderResponse> getallords(){
		return ords.getords();
	}
	
	@PutMapping("/{oid}")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage updord(@PathVariable int oid,@RequestBody Orderupd ord) {
		return ords.updord(oid,ord.getOrderStatus());
	}
	
	@GetMapping("/{userid}")
	@ResponseStatus(HttpStatus.OK)
	public List<OrderResponse> getordsbyuserid(@PathVariable int userid){
		return ords.getords(Long.valueOf(userid));
	}
	
	@GetMapping("/details/{oid}")
	@ResponseStatus(HttpStatus.OK)
	public OrderDetailResponse getorddets(@PathVariable int oid) {
		return ords.getorddets(Long.valueOf(oid));
	}
	
	public String fallbackMethod(OrderRequest ordreq, RuntimeException re) {
		System.out.println("exception: " + re.getMessage());
		return "Oops! Something went wrong, place order after some time!";
	}
	
}
