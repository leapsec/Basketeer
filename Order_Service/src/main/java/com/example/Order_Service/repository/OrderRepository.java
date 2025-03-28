package com.example.Order_Service.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Order_Service.model.Order;
import com.example.Order_Service.model.OrderItem;

public interface OrderRepository extends JpaRepository<Order, Long> {
	Optional<List<Order>> findByUserid(Long userid);
}
