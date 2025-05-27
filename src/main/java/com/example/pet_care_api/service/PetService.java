package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreatePetRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetResponseDTO;
import com.example.pet_care_api.exceptions.DoctorNotFoundException;
import com.example.pet_care_api.exceptions.PetCategoryNotFoundException;
import com.example.pet_care_api.exceptions.PetNotFoundException;
import com.example.pet_care_api.exceptions.PetOwnerNotFoundException;
import com.example.pet_care_api.models.Pet;

import java.io.IOException;
import java.util.List;

public interface PetService {

    PetResponseDTO createPet(Long doctorId,
                             Long petCategoryId,
                             Long petOwnerId, CreatePetRequestDTO createPetRequestDTO)
            throws IOException,
            PetCategoryNotFoundException,
            PetOwnerNotFoundException,
            DoctorNotFoundException;

    PetResponseDTO getPetById(Long petId) throws PetNotFoundException;

    List<PetResponseDTO> findAllPets();

    void deletePetById(Long petId);

    PetResponseDTO updatePet(Long id,CreatePetRequestDTO createPetRequestDTO)
            throws IOException, PetNotFoundException;
}
