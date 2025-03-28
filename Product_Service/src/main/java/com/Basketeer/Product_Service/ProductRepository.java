package com.Basketeer.Product_Service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Basketeer.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
}
