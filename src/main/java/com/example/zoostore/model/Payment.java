package com.example.zoostore.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Order order;

    private Date dateOfPayment;

    private Date dateOfCompletion;

    private boolean isPickup;

    private Status status;

    private boolean isCard;
}
