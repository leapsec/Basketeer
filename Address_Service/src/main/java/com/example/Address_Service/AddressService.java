package com.example.Address_Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Address_Service.clients.Clientreq;
import com.example.Address_Service.dto.AddressRequest;
import com.example.Address_Service.dto.AddressResponse;
import com.example.Address_Service.dto.CustomerResponse;
import com.example.Address_Service.dto.StatusMessage;
import com.example.Address_Service.model.Address;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {
	private final AddressRepository ar;
	private final Clientreq cr;
	public StatusMessage addAddress(AddressRequest areq) {
		CustomerResponse custrep = cr.isuserid(areq.getUserid());
		if(custrep==null) return new StatusMessage(false,"Can't find the user");
		Address nar = new Address();
		nar.setAddress(areq.getAddress());
		nar.setCity(areq.getCity());
		nar.setNotes(areq.getNotes());
		nar.setPhone(areq.getPhone());
		nar.setPincode(areq.getPincode());
		nar.setUserid(areq.getUserid());
		ar.save(nar);
		return new StatusMessage(true,"Address Added Successfully...");
	}
	public List<Address> fetchalladdr(Long uid){
		return ar.findByUserid(uid).orElse(null);
	}
	public Address fetchaddr(Long aid) {
		return ar.findById(aid).orElse(null);
	}
	
	public StatusMessage editaddr(Long uid,Long aid,AddressRequest areq) {
		CustomerResponse custrep = cr.isuserid(uid);
		if(custrep==null) return new StatusMessage(false,"Can't find the user");
		Address uar = ar.findById(aid).orElse(null);
		if(uar==null) return new StatusMessage(false,"Can't find the Address");
		uar.setAddress(areq.getAddress());
		uar.setCity(areq.getCity());
		uar.setNotes(areq.getNotes());
		uar.setPhone(areq.getPhone());
		uar.setPincode(areq.getPincode());
		ar.save(uar);
		return new StatusMessage(true,"Address Updated...");
	}
	public StatusMessage deladdr(Long uid,Long aid) {
		CustomerResponse custrep = cr.isuserid(uid);
		if(custrep==null) return new StatusMessage(false,"Can't find the user");
		Address uar = ar.findById(aid).orElse(null);
		if(uar==null) return new StatusMessage(false,"Can't find the Address");
		ar.delete(uar);
		return new StatusMessage(true,"Deleted Address...");
	}
}
