package com.example.zoostore.utils.model;

import com.example.zoostore.dto.request.ProductDtoRequest;
import com.example.zoostore.model.Product;
import lombok.SneakyThrows;

public class ProductFacade {
    @SneakyThrows
    public static void ProductDtoToProduct(ProductDtoRequest request, Product product) {
        product.setName(request.getName() != null ? request.getName() : product.getName());
        product.setQuantity(request.getQuantity() != null ? request.getQuantity() : product.getQuantity());
        product.setDescription(request.getDescription() != null ? request.getDescription() :product.getDescription());
        product.setPrice(request.getPrice() != null ? request.getPrice() : product.getPrice());
        product.setImage(request.getImage() != null ? request.getImage() : product.getImage());
    }

}
