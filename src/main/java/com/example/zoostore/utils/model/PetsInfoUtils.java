package com.example.zoostore.utils.model;

import com.example.zoostore.dto.request.CreatePetDtoRequest;
import com.example.zoostore.model.PetsInfo;

public class PetsInfoUtils {
    public static void ProductDtoToPetsInfo(CreatePetDtoRequest request, PetsInfo pet) {
        pet.setBreed(request.getBreed() != null ? request.getBreed() : pet.getBreed());
        pet.setCity(request.getCity() != null ? request.getCity() : pet.getCity());
        pet.setGender(request.getGender() != null ? request.getGender() : pet.getGender());
        pet.setOwnerNumber(request.getOwnerNumber() != null ? request.getOwnerNumber() : pet.getOwnerNumber());
    }
}
