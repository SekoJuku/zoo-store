package com.example.zoostore.controller;

import com.example.zoostore.model.PetsInfo;
import com.example.zoostore.model.Product;
import com.example.zoostore.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/{id}")
    public HttpStatus deletePetById(@PathVariable Long id) {
        productService.deletePetById(id);
        return HttpStatus.OK;
    }
}
