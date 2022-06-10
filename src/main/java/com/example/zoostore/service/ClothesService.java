package com.example.zoostore.service;

import com.example.exception.domain.NotFoundException;
import com.example.zoostore.dto.request.ClothesDtoRequest;
import com.example.zoostore.dto.response.ClothesDtoResponse;
import com.example.zoostore.model.Category;
import com.example.zoostore.model.ClothesInfo;
import com.example.zoostore.model.Product;
import com.example.zoostore.repository.CategoryRepository;
import com.example.zoostore.repository.ClothesInfoRepository;
import com.example.zoostore.repository.ProductRepository;
import com.example.zoostore.utils.model.ClothesInfoFacade;
import com.example.zoostore.utils.model.ProductFacade;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ClothesService {
    private final ClothesInfoRepository clothesInfoRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductService productService;

    public List<ClothesInfo> getAllClothes() {
        return clothesInfoRepository.getAllClothes();
    }

    public List<ClothesDtoResponse> getAllClothesResponse() {
        List<ClothesInfo> list = getAllClothes();
        List<ClothesDtoResponse> response = new ArrayList<>();
        for(ClothesInfo clothesInfo : list) {
            response.add(ClothesInfoFacade.clothesInfoToProductResponse(clothesInfo));
        }
        return response;
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(()-> new NotFoundException(String.format("Category with id: %d not found",id)));
    }

    public ClothesDtoResponse getByProductIdResponse(Long id) {
        return ClothesInfoFacade.clothesInfoToProductResponse(getByProductId(id));
    }

    public ClothesInfo getByProductId(Long id) {
        return clothesInfoRepository.findByProductId(id)
                .orElseThrow(() -> new NotFoundException(String.format("Clothes with id: %d not found", id)));
    }

    @Transactional(rollbackFor = {SQLException.class, RuntimeException.class})
    public ClothesInfo addClothes(ClothesDtoRequest request) {
        Category category = getCategoryById(request.getCategoryId());

        if ( !verify(category)) {
            throw new IllegalArgumentException("Category is not clothes");
        }

        Product product = Product.builder()
                .category(category)
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .description(request.getDescription())
                .image(request.getImage())
                .build();

        return saveImageAndReturnClothesInfo(request, product);
    }

    private boolean verify(Category category) {
        return category.getSuperCategory().getId() == 2;
    }

    @SneakyThrows
    public ClothesInfo updateClothes(ClothesDtoRequest request, Long id) {
        Category category = getCategoryById(request.getCategoryId());

        if ( !verify(category)) {
            throw new IllegalArgumentException("Category is not clothes");
        }

        Product product = productService.getProductById(id);

        ProductFacade.ProductDtoToProduct(request, product);

        return saveImageAndReturnClothesInfo(request, product);
    }

    private ClothesInfo saveImageAndReturnClothesInfo(ClothesDtoRequest request, Product product) {
        ClothesInfo clothesInfo = ClothesInfo.builder()
                .product(product)
                .size(request.getSize())
                .build();

        Product save = productRepository.save(product);
        clothesInfoRepository.save(clothesInfo);

        return clothesInfo;
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
                .map(ClothesInfoFacade::clothesInfoToProductResponse)
                .collect(Collectors.toList());
    }
}
