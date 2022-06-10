package com.example.zoostore.utils;


import com.example.zoostore.dto.response.OrderDtoResponse;
import com.example.zoostore.model.Order;
import com.example.zoostore.model.Payment;

public class OrderUtils {
    public static void orderAndPaymentOrderDtoResponse(OrderDtoResponse response, Order order, Payment payment) {
        response.setPaymentId(payment.getId());
        response.setOrderId(order.getId());
        response.setDateOfPayment(payment.getDateOfPayment());
        response.setDateOfCompletion(payment.getDateOfCompletion());
        response.setPickup(payment.isPickup());
        response.setStatus(payment.getStatus());
        response.setCard(payment.isCard());
        response.setTotal(order.getTotal());
        response.setPaid(order.isPaid());
    }
}
