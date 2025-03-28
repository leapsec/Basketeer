package com.example.Cart_Service.dto;

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
public class CartResponse {
	private Long productid;
	private int quantity;
	private String image;
	private String title;
	private float price;
	private float salePrice;
}
