package com.example.zoostore.controller;

import com.example.zoostore.dto.request.CreatePetDtoRequest;
import com.example.zoostore.dto.response.PetDtoResponse;
import com.example.zoostore.model.PetsInfo;
import com.example.zoostore.service.PetService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/pet")
public class PetController {
    private final PetService petService;

    @GetMapping
    public List<PetDtoResponse> getAllPets() {
        return petService.getAllPetsResponse();
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello!";
    }

    @GetMapping("/{id}")
    public PetDtoResponse findPetByProductId(@PathVariable Long id) {
        return petService.findPetByProductIdResponse(id);
    }

    @PostMapping
    public PetsInfo addPet(@ModelAttribute CreatePetDtoRequest request) {
        return petService.addPet(request);
    }

    @GetMapping("/category/{id}")
    public List<PetDtoResponse> getAllPetsByCategory(@PathVariable Long id) {
        return petService.getAllPetsByCategoryIdResponse(id);
    }

    @DeleteMapping("/{id}")
    public HttpStatus deletePetById(@PathVariable Long id) {
        petService.deletePetById(id);
        return HttpStatus.OK;
    }

    @PutMapping("/{id}")
    public PetsInfo updatePetById(@ModelAttribute CreatePetDtoRequest request, @PathVariable Long id) {
        return petService.updatePetById(request, id);
    }
}
