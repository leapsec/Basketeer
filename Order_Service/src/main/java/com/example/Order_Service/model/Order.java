package com.example.Order_Service.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	@Column
	private Long userid;
	@Column
	private float totalAmount;
	@Column
	private String orderstatus;
	@Column
	private String paymentMethod;
	@Column
	private String paymentStatus;
	@Column
	private Date orderdate;
	@Column
	private Date orderupdatedate;
	@Column
	private String paymentId;
	@Column
	private String payerId;
	@Column
	private Long addressid;
	@OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.EAGER)
	List<OrderItem> orditems;
}
