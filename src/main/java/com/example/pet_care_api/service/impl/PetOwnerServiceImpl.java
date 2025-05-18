package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreatePetOwnerRequestDTO;
import com.example.pet_care_api.models.PetOwner;
import com.example.pet_care_api.repositories.PetOwnerRepository;
import com.example.pet_care_api.service.PetOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetOwnerServiceImpl implements PetOwnerService {

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    @Override
    public PetOwner createPetOwner(CreatePetOwnerRequestDTO createPetOwnerRequestDTO) {

        PetOwner petOwner = new PetOwner();
        petOwner.setOwnerName(createPetOwnerRequestDTO.getOwnerName());
        petOwner.setAddress(createPetOwnerRequestDTO.getAddress());
        petOwner.setPhoneNumber(createPetOwnerRequestDTO.getPhoneNumber());

        return petOwnerRepository.save(petOwner);
    }
}
