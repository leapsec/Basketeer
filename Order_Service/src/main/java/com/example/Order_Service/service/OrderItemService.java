package com.example.Order_Service.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.example.Order_Service.dto.OrderItemRequest;
import com.example.Order_Service.dto.OrderItemResponse;
import com.example.Order_Service.model.OrderItem;
import com.example.Order_Service.repository.OrderItemRepository;
import com.example.Order_Service.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderItemService {
	private final OrderItemRepository or;
	private final OrderRepository ordr;
	public String addord(Long orderid,OrderItemRequest ordreq) {
		OrderItem o1 = new OrderItem();
		o1.setOrder(ordr.findById(orderid).orElse(null));
		if(o1.getOrder()==null) return "Can't Find the order";
		o1.setPrice(ordreq.getPrice());
		o1.setQuantity(ordreq.getQuantity());
		o1.setProductid(ordreq.getProductid());
		or.save(o1);
		System.out.println("OrderItems id: " + o1.getId());
		return "Order Item Added";
	}
	
	public String delord(Long id) {
		OrderItem o1 = or.findById(id).orElse(null);
		if(o1==null) return "Failed to find the order Item";
		or.delete(o1);
		return "Order Item Deleted Succesfully";
	}
}
