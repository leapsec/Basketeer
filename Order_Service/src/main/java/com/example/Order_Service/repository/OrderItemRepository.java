package com.example.Order_Service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Order_Service.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
	Optional<List<OrderItem>> findByOrder_id(Long orderid);
}
