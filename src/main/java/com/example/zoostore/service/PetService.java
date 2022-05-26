package com.example.zoostore.service;

import com.example.oauth2.exception.domain.BadRequestException;
import com.example.oauth2.exception.domain.NotFoundException;
import com.example.zoostore.dto.request.CreatePetDtoRequest;
import com.example.zoostore.dto.response.ClothesDtoResponse;
import com.example.zoostore.dto.response.PetDtoResponse;
import com.example.zoostore.model.Category;
import com.example.zoostore.model.ClothesInfo;
import com.example.zoostore.model.PetsInfo;
import com.example.zoostore.model.Product;
import com.example.zoostore.repository.CategoryRepository;
import com.example.zoostore.repository.PetsInfoRepository;
import com.example.zoostore.repository.ProductRepository;
import com.example.zoostore.utils.model.ClothesInfoUtil;
import com.example.zoostore.utils.model.PetsInfoUtils;
import com.example.zoostore.utils.model.ProductUtils;
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


//    public List<PetsInfo> getAllPets() {
//        return petsInfoRepository.findAll();
//    }

    public List<PetDtoResponse> getAllPets() {
        List<PetsInfo> list = petsInfoRepository.findAll();
        List<PetDtoResponse> response = new ArrayList<>();
        for(PetsInfo petsInfo : list) {
            response.add(PetsInfoUtils.petsInfoToProductResponse(petsInfo));
        }
        return response;
    }

    public PetDtoResponse findPetByProductId(Long id) {
        Optional<Product> oProduct = productRepository.findById(id);

        if(oProduct.isEmpty()) throw new NotFoundException("Wrong id number");

        Product product = oProduct.get();

        PetsInfo petsInfo = petsInfoRepository.findPetsInfosByProductId(id);

        return PetsInfoUtils.petsInfoToProductResponse(petsInfo);
    }


    @Transactional
    public void deletePetById(Long id) {
        if(!productRepository.existsById(id))
            throw new NotFoundException(String.format("Pet with id: %d not found",id));
        petsInfoRepository.deleteByProductId(id);
        productRepository.deleteById(id);
    }

    public PetsInfo updatePetById(CreatePetDtoRequest request, Long id) {
        Optional<PetsInfo> oPet = petsInfoRepository.findByProductId(id);
        if(oPet.isEmpty())
            throw new BadRequestException(String.format("Pet with id: %d not found", id));
        PetsInfo pet = oPet.get();
        Product product = pet.getProduct();
        ProductUtils.ProductDtoToProduct(request, product);
        setCategoryToProduct(request.getCategoryId(), product);
        PetsInfoUtils.ProductDtoToPetsInfo(request, pet);
        pet.setProduct(product);
        productRepository.save(product);
        return petsInfoRepository.save(pet);
    }

    private void setCategoryToProduct(Long id, Product product) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty())
            throw new BadRequestException(String.format("Category with id: %d not found",id));
        product.setCategory(categoryRepository.getById(id));
    }

    public PetsInfo addPet(CreatePetDtoRequest request) {
        PetsInfo pet = new PetsInfo();
        Product product = new Product();
        PetsInfoUtils.ProductDtoToPetsInfo(request, pet);
//        log.info(request.getImage().getName());
//        log.info(request.getImage().getOriginalFilename());
//        log.info(request.getImage().getContentType());
        ProductUtils.ProductDtoToProduct(request, product);
        Product savedProduct = productRepository.save(product);
        pet.setProduct(savedProduct);
        return petsInfoRepository.save(pet);
    }

//    public List<PetsInfo> getAllPetsByCategoryId(Long id) {
//        Optional<Category> optionalCategory = categoryRepository.findById(id);
//        if (optionalCategory.isEmpty()) {
//            throw new BadRequestException(String.format("Category by: %d is not found!", id));
//        }
//        Category category = optionalCategory.get();
//        return getAllPets()
//                .stream()
//                .filter(
//                        e-> e.getProductId().getCategory().getId()
//                                .equals(category.getId()))
//                .collect(Collectors.toList());
//    }
}
