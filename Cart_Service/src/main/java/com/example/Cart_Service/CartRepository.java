package com.example.Cart_Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Cart_Service.model.Cart;


public interface CartRepository extends JpaRepository<Cart,Long> {
	Optional<List<Cart>> findByUserid(Long userid);
}
