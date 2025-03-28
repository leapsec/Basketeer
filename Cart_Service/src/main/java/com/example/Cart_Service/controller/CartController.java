package com.example.Cart_Service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.example.Cart_Service.CartService;
import com.example.Cart_Service.clients.Clientreq;
import com.example.Cart_Service.dto.CartRequest;
import com.example.Cart_Service.dto.CartResponse;
import com.example.Cart_Service.dto.CustomerResponse;
import com.example.Cart_Service.dto.ProductResponse;
import com.example.Cart_Service.dto.StatusMessage;
import com.example.Cart_Service.model.Cart;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
	private final CartService cs;
	private final Clientreq cli;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StatusMessage addToCart(@RequestBody CartRequest cartreq) {
        if(cartreq!=null) {
    	CustomerResponse crep = cli.isuserid(Long.valueOf(cartreq.getUserid()));

        if (crep==null) {
            return new StatusMessage(false,"Userid Not Found");
        }

        boolean prodinv = cli.isInStock(Long.valueOf(cartreq.getProductid()),Integer.valueOf( cartreq.getQuantity()));

        if (!prodinv) {
            return new StatusMessage(false,"Insufficient stock for the requested quantity of the product");
        }
        
        return new StatusMessage(true,cs.addcart(Long.valueOf(cartreq.getUserid()), Long.valueOf(cartreq.getProductid()),Integer.valueOf( cartreq.getQuantity())));
        }
        return new StatusMessage(false,"Please send the CartRequest as the request body...");
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<CartResponse> fetchCartItems(@PathVariable Long userId) {
    	List<CartResponse> l1 = new ArrayList<CartResponse>();
    	List<Cart> l2 = cs.fetchc(userId);
    	for(Cart c1:l2) {
    		CartResponse crep = new CartResponse();
    		ProductResponse prep = cli.getprod(c1.getProductid());
    		if(prep!=null) {
    			crep.setImage(prep.getImage());
    			crep.setPrice(prep.getPrice());
    			crep.setProductid(c1.getProductid());
    			crep.setQuantity(c1.getQuantity());
    			crep.setSalePrice(prep.getSalePrice());
    			crep.setTitle(prep.getTitle());
    		}
    		else return null;
    		l1.add(crep);
    	}
    	return l1;
    }

    @DeleteMapping("/{userid}/{prodid}")
    @ResponseStatus(HttpStatus.OK)
    public StatusMessage deleteCartItem(@PathVariable("userid") int userId,@PathVariable("prodid") int productId) {
        return cs.deletec(Long.valueOf(userId), Long.valueOf( productId));
    }

    @DeleteMapping("/{userid}")
    @ResponseStatus(HttpStatus.OK)
    public StatusMessage deleteCart(@PathVariable("userid") int userId) {
        return cs.deletecart(Long.valueOf(userId));
    }
    
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public StatusMessage updateCartQuantity(@RequestBody CartRequest cartreq) {
    	if(cartreq==null) return new StatusMessage(false,"Please send the CartRequest as the request body...");
    	boolean prodinv = cli.isInStock(Long.valueOf( cartreq.getProductid()),Integer.valueOf( cartreq.getQuantity()));

        if (!prodinv) {
            return new StatusMessage(false,"Insufficient stock for the requested quantity of the product");
        }

        return cs.updc(Long.valueOf( cartreq.getUserid()),Long.valueOf( cartreq.getProductid()),Integer.valueOf( cartreq.getQuantity()));
        
    }
}
