package com.example.zoostore.service;

import com.example.zoostore.model.PetsInfo;
import com.example.zoostore.repository.PetsInfoRepository;
import com.example.zoostore.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PetsInfoRepository petsInfoRepository;


    public List<PetsInfo> getAllPets() {
        return petsInfoRepository.findAll();
    }

    public List<PetsInfo> getAllPetsByCategory(String category) {
//        for (PetsInfo e :
//            getAllPets()) {
//            if(!e.getProduct().getCategory().getName().equals(category)) {
//                pets.add(e);
//            }
//        }
        return getAllPets().stream().filter(e-> !e.getProduct().getCategory().getName().equals(category)).collect(Collectors.toList());
    }
}
