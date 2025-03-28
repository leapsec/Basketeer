package com.Basketeer.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.Basketeer.Product_Service.ImageFileRepository;
import com.Basketeer.model.ImageFile;

import io.jsonwebtoken.io.IOException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageFileService {
	private final ImageFileRepository imr;
	public String saveImage(byte[] imageData, String originalFilename) throws IOException {
        String uniqueId = UUID.randomUUID().toString();  

        ImageFile imageFile = new ImageFile();
        imageFile.setId(uniqueId);
        imageFile.setOriginalFilename(originalFilename);
        imageFile.setData(imageData);
        
        imr.save(imageFile);  

        return uniqueId;  
    }

    public byte[] getImage(String id) {
        return imr.findById(id).map(ImageFile::getData).orElse(null);
    }
}
