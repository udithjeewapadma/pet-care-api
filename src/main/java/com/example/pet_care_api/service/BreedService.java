package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateBreedRequestDTO;
import com.example.pet_care_api.controllers.dto.response.BreedResponseDTO;
import com.example.pet_care_api.exceptions.BreedNotFoundException;
import com.example.pet_care_api.exceptions.PetClinicNotFoundException;
import com.example.pet_care_api.models.Breed;

import java.util.List;

public interface BreedService {

    Breed createBreed(Long petCategoryId,CreateBreedRequestDTO createBreedRequestDTO) throws PetClinicNotFoundException;

    BreedResponseDTO getBreedById(Long id) throws BreedNotFoundException;

    List<BreedResponseDTO> getAllBreeds();

    void deleteBreedById(Long id);

    Breed updateBreeById(Long id, CreateBreedRequestDTO createBreedRequestDTO) throws BreedNotFoundException;
}
