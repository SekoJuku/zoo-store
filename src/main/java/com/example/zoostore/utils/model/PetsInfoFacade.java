package com.example.zoostore.utils.model;

import com.example.zoostore.dto.request.CreatePetDtoRequest;
import com.example.zoostore.dto.response.PetDtoResponse;
import com.example.zoostore.model.PetsInfo;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class PetsInfoFacade {
    public static void ProductDtoToPetsInfo(CreatePetDtoRequest request, PetsInfo pet) {
        pet.setBreed(request.getBreed() != null ? request.getBreed() : pet.getBreed());
        pet.setCity(request.getCity() != null ? request.getCity() : pet.getCity());
        pet.setGender(request.getGender() != null ? request.getGender() : pet.getGender());
        pet.setOwnerNumber(request.getOwnerNumber() != null ? request.getOwnerNumber() : pet.getOwnerNumber());
    }

    public static PetDtoResponse petsInfoToProductResponse(PetsInfo petsInfo) {
        return new PetDtoResponse(
                petsInfo.getId(),
                petsInfo.getProduct().getId(),
                petsInfo.getProduct().getCategory().getId(),
                petsInfo.getProduct().getName(),
                petsInfo.getProduct().getImage(),
                petsInfo.getProduct().getPrice(),
                petsInfo.getProduct().getDescription(),
                petsInfo.getGender(),
                petsInfo.getCity(),
                petsInfo.getOwnerNumber(),
                petsInfo.getBreed(),
                petsInfo.getProduct().getCreatedTime(),
                petsInfo.getProduct().getUpdatedTime()
        );
    }
}
