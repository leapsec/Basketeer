package com.example.Order_Service.service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.Order_Service.clients.Clientreq;
import com.example.Order_Service.dto.Address;
import com.example.Order_Service.dto.CustomerResponse;
import com.example.Order_Service.dto.OrderDetailResponse;
import com.example.Order_Service.dto.OrderItemRequest;
import com.example.Order_Service.dto.OrderItemResponse;
import com.example.Order_Service.dto.OrderRequest;
import com.example.Order_Service.dto.OrderResponse;
import com.example.Order_Service.dto.ProductResponse;
import com.example.Order_Service.dto.StatusMessage;
import com.example.Order_Service.model.Order;
import com.example.Order_Service.model.OrderItem;
import com.example.Order_Service.repository.OrderItemRepository;
import com.example.Order_Service.repository.OrderRepository;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository or;
	private final OrderItemService ors;
	private final OrderItemRepository orirep;
	private final Clientreq cli;
	private final PaypalService ps;
	public StatusMessage addord(OrderRequest ordreq) {
		Order o1 = new Order();
		o1.setOrderstatus(ordreq.getOrderstatus());
		o1.setPaymentId(ordreq.getPaymentId());
		o1.setPaymentMethod(ordreq.getPaymentMethod());
		o1.setPaymentStatus(ordreq.getPaymentStatus());
		o1.setOrderdate(Date.valueOf(LocalDate.now()));
		o1.setOrderupdatedate(Date.valueOf(LocalDate.now()));
		o1.setTotalAmount(ordreq.getTotal());
		o1.setAddressid(ordreq.getAddressid());
		o1.setUserid(ordreq.getUserid());
		or.save(o1);
		CustomerResponse crep = cli.getcust(ordreq.getUserid());
		if(crep==null) return new StatusMessage(false,"User id not found...");
		for(OrderItemRequest ori:ordreq.getOrds()) {
			if(!cli.isInStock(ori.getProductid(), ori.getQuantity())) {
				or.delete(o1);
				return new StatusMessage(false,"Stock Not available for Product: " + ori.getProductid());
			}
		}
		for(OrderItemRequest ori: ordreq.getOrds()) {
			System.out.println(ori.getPrice() + " : " + ori.getQuantity() + " : " + ori.getProductid()+": id:" + o1.getId());
			if(!ors.addord(o1.getId(), ori).equals("Order Item Added")) {
				or.delete(o1);
				return new StatusMessage(false,"Problem in inserting order items");
			}
		}
		for(OrderItemRequest ori:ordreq.getOrds()) {
			cli.updStock(ori.getProductid(), ori.getQuantity());
		}
		StatusMessage sm = cli.delcart(ordreq.getUserid());
		if(!sm.isSuccess()) return new StatusMessage(false,"Problem While Deleting the cart");
		try {
			Payment p1 =  ps.createPayment(Double.valueOf( (double)ordreq.getTotal()), "Order to basketeer", ordreq.getOrds());
			Optional<String> approvalLink = p1.getLinks().stream()
	                .filter(link -> "approval_url".equals(link.getRel()))
	                .map(Links::getHref)
	                .findFirst();
			return new StatusMessage(true,approvalLink.orElse("")+";" + o1.getId().intValue());
		} catch (PayPalRESTException e) {
			or.delete(o1);
			e.printStackTrace();
			return new StatusMessage(false,"Error creating payment for paypal...");
		}
	}
	
	public StatusMessage capturePayment(String payerId,String PaymentId,String OrderId) {
		Order o1 = or.findById(Long.valueOf(OrderId)).orElse(null);
		if(o1==null) return new StatusMessage(false,"Can't find the order");
		o1.setPaymentId(PaymentId);
		o1.setPayerId(payerId);
		try {
			ps.executePayment(PaymentId, payerId);
			o1.setOrderstatus("confirmed");
			o1.setPaymentStatus("Success");
		} catch (PayPalRESTException e) {
			o1.setOrderstatus("rejected");
			o1.setPaymentStatus("denied");
			or.save(o1);
			e.printStackTrace();
			return new StatusMessage(false,"Payment Failed...");
		}
		or.save(o1);
		return new StatusMessage(true,"Payment Done...");
	}
	
	public List<OrderResponse> getords(Long userid){
		List<OrderResponse> l1 = new ArrayList<OrderResponse>();
		List<Order> l2 = or.findByUserid(userid).orElse(null);
		for(Order o1:l2) {
			OrderResponse orep = new OrderResponse();
			orep.setOrderdate(o1.getOrderdate());
			orep.setOrderid(o1.getId());
			orep.setOrderprice(o1.getTotalAmount());
			orep.setOrderstatus(o1.getOrderstatus());
			l1.add(orep);
		}
		return l1;
	}
	
	public List<OrderResponse> getords(){
		List<OrderResponse> l1 = new ArrayList<OrderResponse>();
		List<Order> l2 = or.findAll();
		for(Order o1:l2) {
			OrderResponse orep = new OrderResponse();
			orep.setOrderdate(o1.getOrderdate());
			orep.setOrderid(o1.getId());
			orep.setOrderprice(o1.getTotalAmount());
			orep.setOrderstatus(o1.getOrderstatus());
			l1.add(orep);
		}
		return l1;
	}
	
	public StatusMessage updord(int oid,String orderStatus) {
		Order o1 = or.findById(Long.valueOf(oid)).orElse(null);
		if(o1==null) return new StatusMessage(false,"Can't find the order...");
		o1.setOrderstatus(orderStatus);
		o1.setOrderupdatedate(Date.valueOf(LocalDate.now()));
		or.save(o1);
		return new StatusMessage(true,"Order Updated...");
	}
	
	public OrderDetailResponse getorddets(Long oid) {
		Order o1 = or.findById(oid).orElse(null);
		if(o1==null) return null;
		List<OrderItem> l1 = orirep.findByOrder_id(oid).orElse(null);
		if(l1==null) return null;
		List<OrderItemResponse> lord = new ArrayList<OrderItemResponse>();
		for(OrderItem oi:l1) {
			OrderItemResponse oirep = new OrderItemResponse();
			ProductResponse prep = cli.getprod(Long.valueOf(oi.getProductid()));
			if(prep==null) return null;
			oirep.setTitle(prep.getTitle());
			oirep.setQuantity(oi.getQuantity());
			oirep.setPrice(oi.getPrice());
			lord.add(oirep);
		}
		OrderDetailResponse odrep = new OrderDetailResponse();
		odrep.setItems(lord);
		odrep.setOrderid(oid);
		odrep.setOrderStatus(o1.getOrderstatus());
		odrep.setOrderdate(o1.getOrderdate());
		odrep.setPaymentMethod(o1.getPaymentMethod());
		odrep.setPaymentStatus(o1.getPaymentStatus());
		odrep.setTotalAmount(o1.getTotalAmount());
		Address addr = cli.getaddr(o1.getAddressid());
		if(addr==null) return null;
		odrep.setAddress(addr.getAddress());
		odrep.setCity(addr.getCity());
		odrep.setNotes(addr.getNotes());
		odrep.setPhone(addr.getPhone());
		odrep.setPincode(addr.getPincode());
		return odrep;
	}

}
