package com.example.Order_Service.dto;

import java.sql.Timestamp;

import com.example.Order_Service.model.Order;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class OrderItemRequest {
	private Long productid;
	private int quantity;
	private float price;
}
