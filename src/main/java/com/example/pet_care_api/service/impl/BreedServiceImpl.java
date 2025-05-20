package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreateBreedRequestDTO;
import com.example.pet_care_api.controllers.dto.response.BreedResponseDTO;
import com.example.pet_care_api.exceptions.BreedNotFoundException;
import com.example.pet_care_api.models.Breed;
import com.example.pet_care_api.repositories.BreedRepository;
import com.example.pet_care_api.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public BreedResponseDTO getBreedById(Long id) {

        Breed breed = breedRepository.findById(id)
                .orElseThrow(() -> new BreedNotFoundException("Breed Not Found"));
        BreedResponseDTO breedResponseDTO = new BreedResponseDTO();
        breedResponseDTO.setId(breed.getId());
        breedResponseDTO.setBreedName(breed.getBreedName());
        return breedResponseDTO;
    }

    @Override
    public List<BreedResponseDTO> getAllBreeds() {
        List<Breed> breeds = breedRepository.findAll();
        return breeds.stream().map(breed -> {
            BreedResponseDTO breedResponseDTO = new BreedResponseDTO();
            breedResponseDTO.setId(breed.getId());
            breedResponseDTO.setBreedName(breed.getBreedName());
            return breedResponseDTO;
        }).collect(Collectors.toList());
    }


}
