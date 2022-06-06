package com.example.zoostore.model;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "images")
@Getter
@Setter
@RequiredArgsConstructor
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private String name;

    private String suffix;

    //private byte[] data;

    public Image(Product product, String name) {
        this.product = product;
        this.name = name;
        //this.suffix = suffix;
        //this.data = data;
    }
}
