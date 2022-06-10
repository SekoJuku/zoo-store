package com.example.zoostore.utils.model;

import com.example.zoostore.dto.request.OrderDtoRequest;
import com.example.zoostore.dto.response.OrderProductDtoResponse;
import com.example.zoostore.model.OrderProduct;

public class OrderProductFacade {
    // create OrderProduct by OrderDtoRequest
    public static OrderProduct orderDtoRequestToOrderProduct(OrderDtoRequest request) {
        return OrderProduct.builder()
                .quantity(request.getQuantity())
                .build();
    }

    public static OrderProductDtoResponse orderProductToOrderProductDtoResponse(OrderProduct orderProduct) {
        return OrderProductDtoResponse.builder()
                .id(orderProduct.getId())
                .product(orderProduct.getProduct())
                .quantity(orderProduct.getQuantity())
                .build();
    }
}
