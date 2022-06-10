package com.example.zoostore.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderDtoRequest extends PaymentDtoRequest {
    private List<OrderDtoRequest> orders;
}
