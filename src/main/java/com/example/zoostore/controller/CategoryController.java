package com.example.zoostore.controller;

import com.example.zoostore.model.Category;
import com.example.zoostore.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<Category> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public Category findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("/super/{id}")
    public List<Category> findBySuperCategoryId(@PathVariable Long id) {
        return categoryService.findBySuperCategoryId(id);
    }
}
