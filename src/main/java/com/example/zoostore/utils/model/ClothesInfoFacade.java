package com.example.zoostore.utils.model;

import com.example.zoostore.dto.request.ClothesDtoRequest;
import com.example.zoostore.dto.response.ClothesDtoResponse;
import com.example.zoostore.model.ClothesInfo;

public class ClothesInfoFacade {
    public static ClothesDtoResponse clothesInfoToProductResponse(ClothesInfo clothesInfo) {
        return ClothesDtoResponse.builder()
                .categoryId(clothesInfo.getProduct().getCategory().getId())
                .image(clothesInfo.getProduct().getImage())
                .description(clothesInfo.getProduct().getDescription())
                .productId(clothesInfo.getProduct().getId())
                .createdTime(clothesInfo.getProduct().getCreatedTime())
                .updatedTime(clothesInfo.getProduct().getUpdatedTime())
                .name(clothesInfo.getProduct().getName())
                .price(clothesInfo.getProduct().getPrice())
                .quantity(clothesInfo.getProduct().getQuantity())
                .size(clothesInfo.getSize())
                .build();
    }

    public static ClothesInfo clothesRequestToClothesInfo(ClothesDtoRequest request, ClothesInfo clothesInfo) {
        return new ClothesInfo();
    }
}
