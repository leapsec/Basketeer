package com.Basketeer.Product_Service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Basketeer.model.ImageFile;
import com.Basketeer.model.Product;

public interface ImageFileRepository extends JpaRepository<ImageFile, String> {

}
