package com.example.zoostore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "pets_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PetsInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Product product;

    private Boolean gender;

    private String city;

    private String ownerNumber;

    private String breed;
}
