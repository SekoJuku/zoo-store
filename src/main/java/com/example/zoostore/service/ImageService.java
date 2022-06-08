package com.example.zoostore.service;

import com.example.zoostore.model.Image;
import com.example.zoostore.model.Product;
import com.example.zoostore.repository.ImageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ImageService {
    public final ImageRepository imageRepository;
    public final ProductService productService;

    // get image by product id
    public Image getImagesByProductId(Long id) {
        return imageRepository.getImageByProductId(id);
    }


}
