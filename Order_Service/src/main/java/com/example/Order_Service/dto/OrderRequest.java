package com.example.Order_Service.dto;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.Column;
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
public class OrderRequest {
	private Long userid;
	private List<OrderItemRequest> ords;
	private String orderstatus;
	private float total;
	private String paymentMethod;
	private String paymentStatus;
	private String paymentId;
	private String payerId;
	private Long addressid;
}
