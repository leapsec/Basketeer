package com.Basketeer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Basketeer.dto.StatusMessage;
import com.Basketeer.service.ImageFileService;
import com.Basketeer.service.ProductService;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product/image")
@RequiredArgsConstructor
public class ImageController {
	private final ImageFileService ims;
	
	@PostMapping
    public ResponseEntity<StatusMessage> uploadImage(@RequestParam("file") MultipartFile file) throws java.io.IOException {
        try {
            String uniqueId = ims.saveImage(file.getBytes(), file.getOriginalFilename());
            return ResponseEntity.ok(new StatusMessage(true,"http://localhost:8080/api/product/image/" + uniqueId));
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new StatusMessage(false,"Failed to upload image: " + e.getMessage()));
        }
    }
	
	@GetMapping("/{id}")
    public ResponseEntity<byte[]> downloadImage(@PathVariable String id) {
        byte[] imageData = ims.getImage(id);
        if (imageData != null) {
            return ResponseEntity.ok()
                    .header("Content-Type", "image/jpeg")  
                    .body(imageData);
        } else {
            return ResponseEntity.status(404).body(null);
        }
    }
}
