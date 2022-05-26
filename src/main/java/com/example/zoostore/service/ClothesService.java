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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClothesService {
    private final ClothesInfoRepository clothesInfoRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<ClothesInfo> getAllClothes() {
        return clothesInfoRepository.getAllClothes();
    }

    public List<ClothesDtoResponse> getAllClothesResponse() {
        List<ClothesInfo> list = getAllClothes();
        List<ClothesDtoResponse> response = new ArrayList<>();
        for(ClothesInfo clothesInfo : list) {
            response.add(ClothesInfoUtil.clothesInfoToProductResponse(clothesInfo));
        }
        return response;
    }

    public ClothesDtoResponse getByProductIdResponse(Long id) {
        return ClothesInfoUtil.clothesInfoToProductResponse(getByProductId(id));
    }

    public ClothesInfo getByProductId(Long id) {
        return clothesInfoRepository.findByProductId(id)
                .orElseThrow(() -> new NotFoundException(String.format("Clothes with id: %d not found", id)));
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
        if(clothesInfoRepository.findByProductId(id).isEmpty())
            throw new NotFoundException(String.format("Clothes with id: %d not found",id));

        clothesInfoRepository.deleteClothesInfoByProductId(id);
        productRepository.deleteById(id);
    }

    public List<ClothesInfo> getAllClothesByCategoryId(Long id) {
        return clothesInfoRepository.getAllClothesByCategoryId(id);
    }

    public List<ClothesDtoResponse> getAllClothesByCategoryIdResponse(Long id) {
        return getAllClothesByCategoryId(id)
                .stream()
                .map(ClothesInfoUtil::clothesInfoToProductResponse)
                .collect(Collectors.toList());
    }
}
