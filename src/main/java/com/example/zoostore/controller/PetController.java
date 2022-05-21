package com.example.zoostore.controller;

import com.example.zoostore.model.PetsInfo;
import com.example.zoostore.model.Product;
import com.example.zoostore.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/pet")
public class PetController {
    private final ProductService productService;

    @GetMapping()
    public List<PetsInfo> getAllPets() {
        return productService.getAllPets();
    }

    @GetMapping("/{category}")
    public List<PetsInfo> getAllPetsByCategory(@PathVariable String category) {
        return productService.getAllPetsByCategory(category);
    }
}
