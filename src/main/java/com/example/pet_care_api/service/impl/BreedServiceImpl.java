package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreateBreedRequestDTO;
import com.example.pet_care_api.models.Breed;
import com.example.pet_care_api.repositories.BreedRepository;
import com.example.pet_care_api.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BreedServiceImpl implements BreedService {
    @Autowired
    private BreedRepository breedRepository;

    @Override
    public Breed createBreed(CreateBreedRequestDTO createBreedRequestDTO) {

        Breed breed = new Breed();
        breed.setBreedName(createBreedRequestDTO.getBreedName());
        return breedRepository.save(breed);
    }
}
