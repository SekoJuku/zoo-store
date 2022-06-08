package com.example.zoostore.service;

import com.example.zoostore.model.Image;
import com.example.zoostore.model.Product;
import com.example.zoostore.repository.ImageRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
public class ImageService {
    public final ImageRepository imageRepository;

    public Image getImagesByProductId(Long id) {
        Image imageByProductId = imageRepository.getImageByProductId(id);
        log.info(imageByProductId.getProduct().getId());
        return imageByProductId;
    }

    public Image editImage(Image image) {
        return imageRepository.save(image);
    }

}