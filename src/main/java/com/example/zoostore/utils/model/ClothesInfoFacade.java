package com.example.zoostore.utils.model;

import com.example.zoostore.dto.request.ClothesDtoRequest;
import com.example.zoostore.dto.response.ClothesDtoResponse;
import com.example.zoostore.model.ClothesInfo;

public class ClothesInfoFacade {
    public static ClothesDtoResponse clothesInfoToProductResponse(ClothesInfo clothesInfo) {
        return new ClothesDtoResponse(
                clothesInfo.getProduct().getId(),
                null,
                clothesInfo.getSize(),
                clothesInfo.getProduct().getCategory().getId(),
                clothesInfo.getProduct().getName(),
                clothesInfo.getProduct().getPrice(),
                clothesInfo.getProduct().getDescription(),
                clothesInfo.getProduct().getQuantity(),
                clothesInfo.getProduct().getCreatedTime(),
                clothesInfo.getProduct().getUpdatedTime());
    }

    public static ClothesInfo clothesRequestToClothesInfo(ClothesDtoRequest request, ClothesInfo clothesInfo) {
        return new ClothesInfo();
    }
}
