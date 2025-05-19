package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateBreedRequestDTO;
import com.example.pet_care_api.models.Breed;

public interface BreedService {

    Breed createBreed(CreateBreedRequestDTO createBreedRequestDTO);
}
