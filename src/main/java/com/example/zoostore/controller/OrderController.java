package com.example.zoostore.controller;


import com.example.zoostore.dto.request.CreateOrderDtoRequest;
import com.example.zoostore.dto.request.OrderDtoRequest;
import com.example.zoostore.dto.response.OrderDtoResponse;
import com.example.zoostore.model.Order;
import com.example.zoostore.model.OrderProduct;
import com.example.zoostore.model.Payment;
import com.example.zoostore.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {
    private final OrderService orderService;


    @PostMapping("/create")
    public HttpStatus createOrder(@RequestBody CreateOrderDtoRequest request) {
        return orderService.createOrder(request);
    }

    @GetMapping
    public List<OrderDtoResponse> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public List<OrderProduct> getAllOrderProducts(@PathVariable("id") Long id) {
        return orderService.getAllOrderProducts(id);
    }

    // pay for order
    @PostMapping("/pay/{id}")
    public HttpStatus payForOrder(@PathVariable("id") Long id) {
        return orderService.payForOrder(id);
    }
}
