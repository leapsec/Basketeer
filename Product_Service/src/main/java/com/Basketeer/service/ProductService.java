package com.Basketeer.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.Basketeer.Product_Service.ProductRepository;
import com.Basketeer.Product_Service.ReviewRepository;
import com.Basketeer.dto.ProductRequest;
import com.Basketeer.dto.ProductResponse;
import com.Basketeer.dto.ReviewRequest;
import com.Basketeer.dto.ReviewResponse;
import com.Basketeer.dto.StatusMessage;
import com.Basketeer.model.Product;
import com.Basketeer.model.Review;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository prodrepo;
	private final ReviewRepository revrepo;
	public void addProduct(ProductRequest prodreq) {
		Product prod = new Product();
		prod.setBrand(prodreq.getBrand());
		prod.setImage(prodreq.getImage());
		prod.setSalePrice(prodreq.getSalePrice());
		prod.setPrice(prodreq.getPrice());
		prod.setDescription(prodreq.getDescription());
		prod.setTotalStock(prodreq.getTotalStock());
		prod.setCategory(prodreq.getCategory());
		prod.setTitle(prodreq.getTitle());
		prodrepo.save(prod);
	}
	public List<ProductResponse> getprods(){
		List<Product> l1 = prodrepo.findAll();
		List<ProductResponse> l2 = new ArrayList<ProductResponse>();
		for(Product p:l1) {
			l2.add(new ProductResponse(p.getId(),p.getImage(),p.getTitle(),p.getDescription(),p.getTotalStock(),p.getPrice(),p.getSalePrice(),p.getCategory(),p.getBrand()));
		}
		return l2;
	}
	
	public List<ProductResponse> getprods2(List<String> cats,List<String> brands,String sortc){
		List<Product> l1 = prodrepo.findAll();
		if (cats != null && !cats.isEmpty()) {
            l1 = l1.stream()
                    .filter(product -> cats.contains(product.getCategory()))
                    .collect(Collectors.toList());
        }
		
		if (brands != null && !brands.isEmpty()) {
            l1 = l1.stream()
                    .filter(product -> brands.contains(product.getBrand()))
                    .collect(Collectors.toList());
        }
		
		Comparator<Product> comparator = getComparator(sortc);
        l1.sort(comparator);
		
		List<ProductResponse> l2 = new ArrayList<ProductResponse>();
		for(Product p:l1) {
			l2.add(new ProductResponse(p.getId(),p.getImage(),p.getTitle(),p.getDescription(),p.getTotalStock(),p.getPrice(),p.getSalePrice(),p.getCategory(),p.getBrand()));
		}
		return l2;
	}
	
	public List<ProductResponse> getprodbykeyw(String keyword){
		List<Product> l1 = prodrepo.findAll();
		l1 = l1.stream().filter(prod->prod.getBrand().contains(keyword.toLowerCase()) || prod.getCategory().toLowerCase().contains(keyword.toLowerCase()) || prod.getDescription().toLowerCase().contains(keyword.toLowerCase()) || prod.getTitle().toLowerCase().contains(keyword.toLowerCase())).collect(Collectors.toList());
		List<ProductResponse> l2 = new ArrayList<ProductResponse>();
		for(Product p:l1) {
			l2.add(new ProductResponse(p.getId(),p.getImage(),p.getTitle(),p.getDescription(),p.getTotalStock(),p.getPrice(),p.getSalePrice(),p.getCategory(),p.getBrand()));
		}
		return l2;
	}
	
	private Comparator<Product> getComparator(String sortBy) {
        switch (sortBy) {
            case "price-hightolow":
                return Comparator.comparingDouble(Product::getPrice).reversed();
            case "title-atoz":
                return Comparator.comparing(Product::getTitle);
            case "title-ztoa":
                return Comparator.comparing(Product::getTitle).reversed();
            default: 
                return Comparator.comparingDouble(Product::getPrice);
        }
    }
	
	public StatusMessage addrev(ReviewRequest rreq) {
		Review r1 = new Review();
		r1.setProductId(rreq.getProductId());
		r1.setRating(rreq.getRating());
		r1.setReviewMsg(rreq.getReviewMsg());
		r1.setUserId(rreq.getUserId());
		r1.setUserName(rreq.getUserName());
		revrepo.save(r1);
		return new StatusMessage(true,"Review Added Successfully...");
	}
	
	public List<ReviewResponse> getrev(Long prodid){
		List<Review> l1 = revrepo.findByProductId(prodid).orElse(null);
		if(l1==null) return null;
		List<ReviewResponse> l2 = new ArrayList<ReviewResponse>();
		for(Review rev:l1) {
			ReviewResponse revresp = new ReviewResponse();
			revresp.setRating(rev.getRating());
			revresp.setReviewMsg(rev.getReviewMsg());
			revresp.setUserName(rev.getUserName());
			l2.add(revresp);
		}
		return l2;
	}
	
	public StatusMessage updprod(Long id,ProductRequest prodreq) {
		Product p1 = prodrepo.findById(id).orElse(null);
		if(p1==null) return new StatusMessage(false,"Update Unsuccesfull");
		p1.setBrand(prodreq.getBrand());
		if(prodreq.getImage()!=null || prodreq.getImage()!="")p1.setImage(prodreq.getImage());
		p1.setSalePrice(prodreq.getSalePrice());
		p1.setPrice(prodreq.getPrice());
		p1.setDescription(prodreq.getDescription());
		p1.setTotalStock(prodreq.getTotalStock());
		p1.setCategory(prodreq.getCategory());
		p1.setTitle(prodreq.getTitle());
		prodrepo.save(p1);
		return new StatusMessage(true,"Updated");
	}
	
	public ProductResponse getprod(Long id) {
		Product p1 = prodrepo.findById(id).orElse(null);
		if(p1==null) return null;
		return new ProductResponse(p1.getId(),p1.getImage(),p1.getTitle(),p1.getDescription(),p1.getTotalStock(),p1.getPrice(),p1.getSalePrice(),p1.getCategory(),p1.getBrand());
	}
	
	public StatusMessage delprod(Long id) {
		Product p1 = prodrepo.findById(id).orElse(null);
		if(p1==null) return new StatusMessage(false,"Not Found");
		prodrepo.delete(p1);
		return new StatusMessage(true,"Deleted");
	}
}
