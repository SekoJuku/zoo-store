package com.example.zoostore.service;

import com.example.zoostore.model.Product;
import com.example.zoostore.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.getProducts();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.getProductById(id);
    }
}
