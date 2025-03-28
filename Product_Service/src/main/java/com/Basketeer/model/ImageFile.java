package com.Basketeer.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Image")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageFile {
	@Id
    private String id; 
    private String originalFilename;
    
    @Lob
    private byte[] data;
}
