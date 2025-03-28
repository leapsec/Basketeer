package com.example.Address_Service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Address_Service.model.Address;


public interface AddressRepository extends JpaRepository<Address, Long> {
	Optional<List<Address>> findByUserid(Long userid);
}
