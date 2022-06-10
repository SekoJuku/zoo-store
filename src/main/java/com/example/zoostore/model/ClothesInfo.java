package com.example.zoostore.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "clothes_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClothesInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

    private String size;

    public ClothesInfo(Product product, String size) {
        this.product = product;
        this.size = size;
    }
}
