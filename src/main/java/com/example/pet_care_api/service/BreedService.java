package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateBreedRequestDTO;
import com.example.pet_care_api.controllers.dto.response.BreedResponseDTO;
import com.example.pet_care_api.models.Breed;

import java.util.List;

public interface BreedService {

    Breed createBreed(CreateBreedRequestDTO createBreedRequestDTO);

    BreedResponseDTO getBreedById(Long id);

    List<BreedResponseDTO> getAllBreeds();

    void deleteBreedById(Long id);

    Breed updateBreeById(Long id, CreateBreedRequestDTO createBreedRequestDTO);
}
