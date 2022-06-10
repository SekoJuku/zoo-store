package com.example.zoostore.controller;

import com.example.zoostore.dto.request.ClothesDtoRequest;
import com.example.zoostore.dto.response.ClothesDtoResponse;
import com.example.zoostore.service.ClothesService;
import com.example.zoostore.utils.model.ClothesInfoFacade;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/clothes")
public class ClothesController {
    public final ClothesService clothesService;

    @GetMapping()
    public List<ClothesDtoResponse> getAllClothes() {
        return clothesService.getAllClothesResponse();
    }

    @GetMapping("/{id}")
    public ClothesDtoResponse getByProductId(@PathVariable Long id) {
        return clothesService.getByProductIdResponse(id);
    }

    @GetMapping("/category/{id}")
    public List<ClothesDtoResponse> getAllClothesByCategoryIdResponse(@PathVariable Long id) {
        return clothesService.getAllClothesByCategoryIdResponse(id);
    }

    @PostMapping()
    public ClothesDtoResponse addClothes(@ModelAttribute ClothesDtoRequest request) {
        return ClothesInfoFacade.clothesInfoToProductResponse(clothesService.addClothes(request));
    }

    @PutMapping("/{id}")
    public ClothesDtoResponse updateClothes(@PathVariable Long id, @ModelAttribute ClothesDtoRequest request) {
        return ClothesInfoFacade.clothesInfoToProductResponse(clothesService.updateClothes(request, id));
    }

    @DeleteMapping("/{id}")
    public void deleteClothesById(@PathVariable Long id) {
        clothesService.deleteClothesById(id);
    }
}
