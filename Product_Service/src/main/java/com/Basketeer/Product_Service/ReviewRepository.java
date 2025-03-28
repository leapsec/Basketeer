package com.Basketeer.Product_Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Basketeer.model.Product;
import com.Basketeer.model.Review;


public interface ReviewRepository extends JpaRepository<Review, Long> {
	Optional<List<Review>> findByProductId(Long productId);
}
