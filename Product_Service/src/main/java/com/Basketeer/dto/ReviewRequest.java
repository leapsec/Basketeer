package com.Basketeer.dto;

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
public class ReviewRequest {
	private Long productId;
	private Long userId;
	private String userName;
	private String reviewMsg;
	private int rating;
}
