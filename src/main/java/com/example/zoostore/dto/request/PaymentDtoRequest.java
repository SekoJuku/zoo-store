package com.example.zoostore.dto.request;

import com.example.zoostore.model.Status;
import lombok.Data;

import java.util.Date;

@Data
public class PaymentDtoRequest {
    private Date dateOfPayment;

    private Date dateOfCompletion;

    private boolean isPickup;

    private Status status;

    private boolean isCard;
}
