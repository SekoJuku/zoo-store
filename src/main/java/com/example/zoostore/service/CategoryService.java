package com.example.zoostore.service;

import com.example.exception.domain.NotFoundException;
import com.example.zoostore.model.Category;
import com.example.zoostore.model.SuperCategory;
import com.example.zoostore.repository.CategoryRepository;
import com.example.zoostore.repository.SuperCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final SuperCategoryRepository superCategoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        Optional<Category> oCategory = categoryRepository.findById(id);

        if(oCategory.isEmpty()) {
            throw new NotFoundException("Category not found");
        }

        Category category = oCategory.get();

        return category;
    }

    public List<Category> findBySuperCategoryId(Long id) {
        return categoryRepository.findBySuperCategoryId(id);
    }

    public List<SuperCategory> findAllSuperCategories() {
        return superCategoryRepository.findAll();
    }
}
