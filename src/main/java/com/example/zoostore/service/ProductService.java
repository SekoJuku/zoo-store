package com.example.zoostore.service;

import com.example.oauth2.exception.domain.BadRequestException;
import com.example.oauth2.exception.domain.NotFoundException;
import com.example.zoostore.dto.request.CreateGoodDtoRequest;
import com.example.zoostore.model.Category;
import com.example.zoostore.model.Product;
import com.example.zoostore.repository.CategoryRepository;
import com.example.zoostore.repository.ProductRepository;
import com.example.zoostore.utils.model.ProductUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getProducts() {
        return productRepository.getProducts();
    }

    public Product getProductById(Long id) {
        Optional<Product> optionalProduct = productRepository.getProductById(id);

        if(optionalProduct.isEmpty()) {
            throw new NotFoundException(String.format("Product with id: %d not found", id));
        }

        Product product = optionalProduct.get();

        if(isValidCategory(product.getCategory())) {
            return product;
        }

        throw new BadRequestException("Product not found");
    }

    public Product addProduct(CreateGoodDtoRequest request) {
        Optional<Category> oCategory = categoryRepository.findById(request.getCategoryId());

        if(oCategory.isEmpty()) {
            throw new BadRequestException("Empty category");
        }

        Category category = oCategory.get();

        if(!isValidCategory(category)) {
            throw new BadRequestException("Wrong category");
        }

        Product product = new Product(
                category,
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getQuantity());

        productRepository.save(product);

        return product;
    }

    public void deleteProductById(Long id) {
        if(!productRepository.existsById(id) || !isValidCategory(productRepository.getProductById(id).get().getCategory()))
            throw new NotFoundException(String.format("Product with %d id not found", id));
        productRepository.deleteById(id);
    }

    public List<Product> getAllByCategoryId(Long id) {
        return productRepository.getAllByCategoryId(id);
    }

    private boolean isValidCategory(Category category) {
        return category.getSuperCategory().getId() == 3;
    }
}
