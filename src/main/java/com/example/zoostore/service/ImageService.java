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

    public List<Image> getAllByProductId(Long id) {
        return imageRepository.getAllByProductId(id);
    }

    public void addImages(List<Image> images, Product product) {
        for(Image image : images) {
            Image newImage = new Image(product, image.getName());
            imageRepository.save(newImage);
        }
    }

}
