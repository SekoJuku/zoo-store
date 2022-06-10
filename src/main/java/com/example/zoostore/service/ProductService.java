package com.example.zoostore.service;

import com.example.exception.domain.BadRequestException;
import com.example.exception.domain.NotFoundException;
import com.example.zoostore.dto.request.CreateGoodDtoRequest;
import com.example.zoostore.model.Category;
import com.example.zoostore.model.Product;
import com.example.zoostore.repository.CategoryRepository;
import com.example.zoostore.repository.ProductRepository;
import com.example.zoostore.utils.model.ProductFacade;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
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
        Optional<Product> optionalProduct = productRepository.findProductById(id);

        if(optionalProduct.isEmpty()) {
            throw new NotFoundException(String.format("Product with id: %d not found", id));
        }

        Product product = optionalProduct.get();

        if(!isValidCategory(product.getCategory())) {
            throw new BadRequestException("Product not found");
        }
        return product;
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(()->new BadRequestException(String.format("Category with id: %d is not found",id)));
    }

    public Product addProduct(CreateGoodDtoRequest request) {
        Category category = getCategoryById(request.getCategoryId());

        if(!isValidCategory(category)) {
            throw new BadRequestException("Wrong category");
        }

        Product product = Product.builder()
                .category(category)
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .image(request.getImage())
                .build();


        Product save = productRepository.save(product);

        return product;
    }

    public Product updateProduct(CreateGoodDtoRequest request, Long id) {
        Category category = getCategoryById(request.getCategoryId());

        if(!isValidCategory(category)) {
            throw new BadRequestException("Wrong category");
        }

        Product product = getProductById(id);

        ProductFacade.ProductDtoToProduct(request, product);

        Product save = productRepository.save(product);

        return product;
    }



    public void deleteProductById(Long id) {
        if(!productRepository.existsById(id) || getProductById(id) != null)
            throw new NotFoundException(String.format("Product with %d id not found", id));
        productRepository.deleteById(id);
    }

    public List<Product> getAllByCategoryId(Long id) {
        return productRepository.getAllByCategoryId(id);
    }

    private boolean isValidCategory(Category category) {
        return category.getSuperCategory().getId() == 3;
    }

    public void updateProduct(Product product) {
        productRepository.save(product);
    }
}
