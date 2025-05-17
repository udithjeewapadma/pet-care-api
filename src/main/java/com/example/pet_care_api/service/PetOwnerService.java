package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreatePetOwnerRequestDTO;
import com.example.pet_care_api.models.PetOwner;

public interface PetOwnerService {

    PetOwner createPetOwner(CreatePetOwnerRequestDTO createPetOwnerRequestDTO);
}
