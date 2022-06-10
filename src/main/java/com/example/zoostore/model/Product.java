package com.example.zoostore.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "products")
@Data
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

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne(targetEntity = Image.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id", referencedColumnName = "id")
    private Image image;

    private Integer quantity;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    @PrePersist
    public void prePersist() {
        this.setCreatedTime(LocalDateTime.now());
    }
    @PreUpdate
    public void preUpdate() {
        this.setUpdatedTime(LocalDateTime.now());
    }
}
