package com.example.zoostore.service;

import com.example.exception.domain.BadRequestException;
import com.example.exception.domain.NotFoundException;
import com.example.zoostore.dto.request.CreatePetDtoRequest;
import com.example.zoostore.dto.response.PetDtoResponse;
import com.example.zoostore.model.Category;
import com.example.zoostore.model.PetsInfo;
import com.example.zoostore.model.Product;
import com.example.zoostore.repository.CategoryRepository;
import com.example.zoostore.repository.PetsInfoRepository;
import com.example.zoostore.repository.ProductRepository;
import com.example.zoostore.utils.model.PetsInfoFacade;
import com.example.zoostore.utils.model.ProductFacade;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Log4j2
public class PetService {
    private final ProductRepository productRepository;
    private final PetsInfoRepository petsInfoRepository;
    private final CategoryRepository categoryRepository;



    public List<PetsInfo> getAllPets() {
        return petsInfoRepository.getAllPets();
    }

    public List<PetDtoResponse> getAllPetsResponse() {
        List<PetsInfo> list = getAllPets();
        List<PetDtoResponse> response = new ArrayList<>();
        for(PetsInfo petsInfo : list) {
            response.add(PetsInfoFacade.petsInfoToProductResponse(petsInfo));
        }
        return response;
    }

    public PetDtoResponse findPetByProductIdResponse(Long id) {
        return PetsInfoFacade.petsInfoToProductResponse(findPetByProductId(id));
    }

    public PetsInfo findPetByProductId(Long id) {
        return petsInfoRepository.findByProductId(id)
                .orElseThrow(() -> new NotFoundException(String.format("Pet with id: %d not found", id)));
    }

    @Transactional
    public void deletePetById(Long id) {
        if(productRepository.findProductById(id).isEmpty())
            throw new NotFoundException(String.format("Pet with id: %d not found",id));

        petsInfoRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }

    public PetsInfo updatePetById(CreatePetDtoRequest request, Long id) {
        PetsInfo pet = findPetByProductId(id);

        Product product = pet.getProduct();
        ProductFacade.ProductDtoToProduct(request, product);
        setCategoryToProduct(request.getCategoryId(), product);
        PetsInfoFacade.ProductDtoToPetsInfo(request, pet);
        pet.setProduct(product);
        Product save = productRepository.save(product);
        return petsInfoRepository.save(pet);
    }

    private void setCategoryToProduct(Long id, Product product) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (optionalCategory.isEmpty())
            throw new NotFoundException(String.format("Category with id: %d not found",id));

        product.setCategory(categoryRepository.getById(id));
    }



    @Transactional
    public PetsInfo addPet(CreatePetDtoRequest request) {
        PetsInfo pet = new PetsInfo();
        Product product = new Product();
        PetsInfoFacade.ProductDtoToPetsInfo(request, pet);
        Category category = getCategoryById(request.getCategoryId());
        product.setCategory(category);
        ProductFacade.ProductDtoToProduct(request, product);
        Product savedProduct = productRepository.save(product);
        pet.setProduct(savedProduct);
        return petsInfoRepository.save(pet);
    }

    public List<PetsInfo> getAllPetsByCategoryId(Long id) {
        return petsInfoRepository.getAllPetsByCategoryId(id);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() ->  new BadRequestException(String.format("Category with id: %d is not found",id)));
    }

    public List<PetDtoResponse> getAllPetsByCategoryIdResponse(Long id) {
        return getAllPetsByCategoryId(id)
                .stream()
                .map(PetsInfoFacade::petsInfoToProductResponse)
                .collect(Collectors.toList());
    }
}