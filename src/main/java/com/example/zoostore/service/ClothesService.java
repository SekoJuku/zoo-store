package com.example.zoostore.service;

import com.example.oauth2.exception.domain.NotFoundException;
import com.example.zoostore.dto.request.ClothesDtoRequest;
import com.example.zoostore.dto.response.ClothesDtoResponse;
import com.example.zoostore.model.Category;
import com.example.zoostore.model.ClothesInfo;
import com.example.zoostore.model.Product;
import com.example.zoostore.repository.CategoryRepository;
import com.example.zoostore.repository.ClothesInfoRepository;
import com.example.zoostore.repository.ProductRepository;
import com.example.zoostore.utils.model.ClothesInfoUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClothesService {
    private final ClothesInfoRepository clothesInfoRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ClothesDtoResponse> getAllClothes() {
        List<ClothesInfo> list = clothesInfoRepository.findAll();
        List<ClothesDtoResponse> response = new ArrayList<>();
        for(ClothesInfo clothesInfo : list) {
            response.add(ClothesInfoUtil.clothesInfoToProductResponse(clothesInfo));
        }
        return response;
    }

    public ClothesDtoResponse getClothesById(Long id) {
        Optional<ClothesInfo> optionalClothesInfo = clothesInfoRepository.findById(id);

        if(optionalClothesInfo.isEmpty()) {
            throw new NotFoundException("Clothes not found");
        }

        ClothesInfo clothesInfo = optionalClothesInfo.get();

        return ClothesInfoUtil.clothesInfoToProductResponse(clothesInfo);
    }

    public ClothesDtoResponse addClothes(ClothesDtoRequest request) {
        Category category = categoryRepository.getById(request.getCategoryId());

        Product product = new Product(
                category,
                request.getName(),
                request.getPrice(),
                request.getDescription(),
                request.getQuantity());

        ClothesInfo clothesInfo = new ClothesInfo(product, request.getSize());

        productRepository.save(product);
        clothesInfoRepository.save(clothesInfo);

        return ClothesInfoUtil.clothesInfoToProductResponse(clothesInfo);
    }
    @Transactional
    public void deleteClothesById(Long id) {
        if(!clothesInfoRepository.existsById(id))
            throw new NotFoundException(String.format("Clothes with id: %d not found",id));

        Long productId = clothesInfoRepository.getById(id).getProduct().getId();
        clothesInfoRepository.deleteById(id);
        productRepository.deleteById(productId);
    }
}
