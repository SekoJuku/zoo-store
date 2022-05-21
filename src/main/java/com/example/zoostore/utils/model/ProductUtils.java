package com.example.zoostore.utils.model;

import com.example.zoostore.dto.request.CreatePetDtoRequest;
import com.example.zoostore.model.Product;
import com.example.zoostore.utils.ImageUtils;
import lombok.SneakyThrows;

public class ProductUtils {
    @SneakyThrows
    public static void ProductDtoToProduct(CreatePetDtoRequest request, Product product) {
        product.setName(request.getName() != null ? request.getName() : product.getName());
        product.setQuantity(request.getQuantity() != null ? request.getQuantity() : product.getQuantity());
        product.setDescription(request.getDescription() != null ? request.getDescription() :product.getDescription());
        product.setImage(request.getImage().getBytes().length != 0 ? ImageUtils.compressImage(request.getImage().getBytes()) : product.getImage());
        product.setPrice(request.getPrice() != null ? request.getPrice() : product.getPrice());
    }
}
