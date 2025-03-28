package com.Basketeer.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Basketeer.dto.ProductRequest;
import com.Basketeer.dto.ProductResponse;
import com.Basketeer.dto.ReviewRequest;
import com.Basketeer.dto.ReviewResponse;
import com.Basketeer.dto.StatusMessage;
import com.Basketeer.model.Product;
import com.Basketeer.service.ProductService;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService prodserv;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public StatusMessage addproduct(@RequestBody ProductRequest prodreq) {
		prodserv.addProduct(prodreq);
		return new StatusMessage(true,"Product Added Successfully");
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ProductResponse> getprods() {
		return prodserv.getprods();
	}
	
	@GetMapping("/filter")
	@ResponseStatus(HttpStatus.OK)
	public List<ProductResponse> getprods2(@RequestParam(value="category",required = false)List<String> cats,@RequestParam(value="brand",required = false)List<String> brands,@RequestParam("sortBy") String sortc) {
		return prodserv.getprods2(cats,brands,sortc);
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ProductResponse getprod(@PathVariable Long id) {
		return prodserv.getprod(id);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage updprod(@PathVariable Long id,@RequestBody ProductRequest prodreq) {
		return prodserv.updprod(id, prodreq);
	}
	
	@GetMapping("/search")
	@ResponseStatus(HttpStatus.OK)
	public List<ProductResponse> getprodbykeyw(@RequestParam("keyword") String keyword){
		return prodserv.getprodbykeyw(keyword);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public StatusMessage delprod(@PathVariable Long id) {
		return prodserv.delprod(id);
	}
	
	@GetMapping("/inv/{id}/{stock}")
	@ResponseStatus(HttpStatus.OK)
	public Boolean checkprod(@PathVariable Long id,@PathVariable int stock) {
		ProductResponse p1 = prodserv.getprod(id);
		if(p1!=null && p1.getTotalStock()>=stock) return true;
		return false;
	}
	
	@PostMapping("/review")
	@ResponseStatus(HttpStatus.CREATED)
	public StatusMessage addreview(@RequestBody ReviewRequest rreq) {
		return prodserv.addrev(rreq);
	}
	
	@GetMapping("/review/{prodid}")
	@ResponseStatus(HttpStatus.OK)
	public List<ReviewResponse> getrev(@PathVariable Long prodid){
		return prodserv.getrev(prodid);
	}
	
	@PutMapping("/inv/{id}/{stock}")
	@ResponseStatus(HttpStatus.OK)
	public String updprod(@PathVariable Long id,@PathVariable int stock) {
		ProductResponse p1 = prodserv.getprod(id);
		if(p1!=null && p1.getTotalStock()>=stock) {
			p1.setTotalStock(p1.getTotalStock()-stock);
			prodserv.updprod(id, new ProductRequest(p1.getImage(),p1.getTitle(),p1.getDescription(),p1.getTotalStock(),p1.getPrice(),p1.getSalePrice(),p1.getCategory(),p1.getBrand()));
			return "Updated Inventory";
		}
		return "Product not found or stock insufficient";
	}
}
