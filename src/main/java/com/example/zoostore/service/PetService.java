package com.example.zoostore.service;

import com.example.oauth2.exception.domain.NotFoundException;
import com.example.zoostore.dto.request.CreatePetDtoRequest;
import com.example.zoostore.model.PetsInfo;
import com.example.zoostore.model.Product;
import com.example.zoostore.repository.PetsInfoRepository;
import com.example.zoostore.repository.ProductRepository;
import com.example.zoostore.utils.model.PetsInfoUtils;
import com.example.zoostore.utils.model.ProductUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class PetService {
    private final ProductRepository productRepository;
    private final PetsInfoRepository petsInfoRepository;


    public List<PetsInfo> getAllPets() {
        return petsInfoRepository.findAll();
    }

    public List<PetsInfo> getAllPetsByCategory(String category) {
        return getAllPets()
                .stream()
                .filter(
                        e-> !e.getProduct().getCategory().getName()
                                .equals(category))
                .collect(Collectors.toList());
    }

    @Transactional
    public void deletePetById(Long id) {
        if(!productRepository.existsById(id)) {
            throw new NotFoundException("Pet with id: " + id + " not found");
        }
        petsInfoRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }

    public PetsInfo updatePetById(CreatePetDtoRequest request, Long id) {
        PetsInfo pet = petsInfoRepository.findByProductId(id);
        Product product = pet.getProduct();
        ProductUtils.ProductDtoToProduct(request, product);
        PetsInfoUtils.ProductDtoToPetsInfo(request, pet);
        pet.setProduct(product);
        productRepository.save(product);
        return petsInfoRepository.save(pet);
    }

    public PetsInfo addPet(CreatePetDtoRequest request) {
        PetsInfo pet = new PetsInfo();
        Product product = new Product();
        PetsInfoUtils.ProductDtoToPetsInfo(request, pet);
        ProductUtils.ProductDtoToProduct(request, product);
        pet.setProduct(productRepository.save(product));
        return petsInfoRepository.save(pet);
    }
}
