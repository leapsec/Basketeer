package com.example.Address_Service.dto;

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
public class AddressResponse {
	private Long addressid;
	
	private Long userid;

	private String address;

	private String City;

	private String Pincode;

	private String Phone;

	private String Notes;
}
