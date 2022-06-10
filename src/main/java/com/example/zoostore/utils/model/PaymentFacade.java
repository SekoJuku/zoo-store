package com.example.zoostore.utils.model;

import com.example.zoostore.dto.request.PaymentDtoRequest;
import com.example.zoostore.model.Payment;

public class PaymentFacade {
    // convert PaymentDtoRequest to Payment
    public static Payment paymentDtoRequestToPayment(PaymentDtoRequest request) {
        return Payment.builder()
                .status(request.getStatus())
                .dateOfPayment(request.getDateOfPayment())
                .dateOfCompletion(request.getDateOfCompletion())
                .isCard(request.isCard())
                .isPickup(request.isPickup())
                .build();
    }
    // parse PaymentDtoRequest to Payment
    public static Payment paymentDtoRequestToPayment(PaymentDtoRequest request, Payment payment) {
        payment.setStatus(request.getStatus());
        payment.setDateOfPayment(request.getDateOfPayment());
        payment.setDateOfCompletion(request.getDateOfCompletion());
        payment.setCard(request.isCard());
        payment.setPickup(request.isPickup());
        return payment;
    }
}
