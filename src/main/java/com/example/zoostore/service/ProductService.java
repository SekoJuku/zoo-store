package com.example.zoostore.service;

import com.example.zoostore.repository.ProductRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
}
