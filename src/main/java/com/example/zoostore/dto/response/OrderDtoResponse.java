package com.example.zoostore.dto.response;


import com.example.zoostore.model.OrderProduct;
import com.example.zoostore.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDtoResponse {
    private Long paymentId;

    private Long orderId;

    private Date dateOfPayment;

    private Date dateOfCompletion;

    private boolean isPickup;

    private Status status;

    private boolean isCard;

    private BigDecimal total;

    private boolean isPaid;

    private List<OrderProductDtoResponse> orderProducts;
}
