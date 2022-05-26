package com.example.zoostore.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    private String name;

    private Double price;

    private String description;

//    @OneToMany
//    @JoinColumn(name = "image_id", referencedColumnName = "id", table = "images")
//    private List<Image> images;

    private Integer quantity;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public Product(Category category, String name, Double price, String description, Integer quantity) {
        this.category = category;
        this.name = name;
        this.price = price;
        this.description = description;
        this.quantity = quantity;
    }

    @PrePersist
    public void prePersist() {
        this.setCreatedTime(LocalDateTime.now());
    }
    @PreUpdate
    public void preUpdate() {
        this.setUpdatedTime(LocalDateTime.now());
    }
}
