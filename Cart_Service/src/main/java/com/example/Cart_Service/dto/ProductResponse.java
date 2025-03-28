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
public class ProductResponse {
	private Long Id;
	private String image;
	private String title;
	private String description;
	private int totalStock;
	private float price;
	private float salePrice;
	private String category;
	private String brand;
}
