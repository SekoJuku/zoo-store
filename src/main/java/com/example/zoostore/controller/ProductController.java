package com.example.zoostore.controller;

import com.example.zoostore.dto.request.CreateGoodDtoRequest;
import com.example.zoostore.model.Product;
import com.example.zoostore.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/category/{id}")
    public List<Product> getAllByCategoryId(@PathVariable Long id) {
        return productService.getAllByCategoryId(id);
    }

    @PostMapping
    public Product addProduct(@RequestBody CreateGoodDtoRequest request) {
        return productService.addProduct(request);
    }

    //edit product
    @PutMapping("/{id}")
    public Product updateProduct(@RequestBody CreateGoodDtoRequest request, @PathVariable Long id) {
        return productService.updateProduct(request, id);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteProductById(id);
    }
}
