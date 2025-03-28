package com.example.Order_Service.dto;

import java.sql.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetailResponse {
	private Long orderid;
	private Date orderdate;
	private float totalAmount;
	private String paymentMethod;
	private String paymentStatus;
	private String orderStatus;
	private List<OrderItemResponse> items;
	private String address;
	private String city;
	private String pincode;
	private String phone;
	private String notes;
}
