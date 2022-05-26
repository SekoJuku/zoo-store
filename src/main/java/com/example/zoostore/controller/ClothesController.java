package com.example.zoostore.controller;

import com.example.zoostore.dto.request.ClothesDtoRequest;
import com.example.zoostore.dto.response.ClothesDtoResponse;
import com.example.zoostore.model.ClothesInfo;
import com.example.zoostore.service.ClothesService;
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
        return clothesService.getAllClothes();
    }

    @GetMapping("/{id}")
    public ClothesDtoResponse getClothesById(@PathVariable Long id) {
        return clothesService.getClothesById(id);
    }

    @PostMapping()
    public ClothesDtoResponse addClothes(ClothesDtoRequest request) {
        return clothesService.addClothes(request);
    }

    @DeleteMapping("/{id}")
    public void deleteClothesById(@PathVariable Long id) {
        clothesService.deleteClothesById(id);
    }
}
