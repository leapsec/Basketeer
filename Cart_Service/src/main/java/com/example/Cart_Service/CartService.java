package com.example.Cart_Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.Cart_Service.dto.StatusMessage;
import com.example.Cart_Service.model.Cart;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	private final CartRepository cr;
	public String addcart(Long userid,Long prodid,int qty) {
		Cart c1 = new Cart();
		c1.setProductid(prodid);
		c1.setUserid(userid);
		c1.setQuantity(qty);
		cr.save(c1);
		return "Cart Updated Successfully";
	}
	
	public List<Cart> fetchc(Long userid){
		return cr.findByUserid(userid).orElse(null);
	}
	
	public StatusMessage deletec(Long userid,Long prodid) {
		List<Cart> l1 = cr.findByUserid(userid).orElse(null);
		Cart c=null;
		for(Cart c1:l1) {
			if(c1.getProductid().equals(prodid)) {
				c = c1;
				break;
			}
		}
		if(c==null) return new StatusMessage(false,"Can't find the item");
		cr.delete(c);
		return new StatusMessage(true,"Deleted Successfully");
	}
	
	public StatusMessage deletecart(Long userid) {
		List<Cart> l1 = cr.findByUserid(userid).orElse(null);
		for(Cart c1:l1) {
			cr.delete(c1);
		}
		return new StatusMessage(true,"Deleted cart Successfully");
	}
	public StatusMessage updc(Long userid,Long prodid,int qty) {
		List<Cart> l1 = cr.findByUserid(userid).orElse(null);
		Cart c=null;
		for(Cart c1:l1) {
			if(c1.getProductid().equals(prodid)) {
				c = c1;
				break;
			}
		}
		if(c==null) return new StatusMessage(false,"Can't find the item");
		c.setQuantity(qty);
		cr.save(c);
		return new StatusMessage(true,"Updated Cart item");
	}
}
