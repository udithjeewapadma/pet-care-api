package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreatePetOwnerRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetOwnerResponseDTO;
import com.example.pet_care_api.exceptions.PetOwnerNotFoundException;
import com.example.pet_care_api.models.PetOwner;
import com.example.pet_care_api.repositories.PetOwnerRepository;
import com.example.pet_care_api.service.PetOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public PetOwnerResponseDTO findPetOwnerById(Long id) throws PetOwnerNotFoundException {

        PetOwner petOwner = petOwnerRepository.findById(id)
                .orElseThrow(() -> new PetOwnerNotFoundException("Pet Owner Not Found"));

        PetOwnerResponseDTO petOwnerResponseDTO = new PetOwnerResponseDTO();
        petOwnerResponseDTO.setId(petOwner.getId());
        petOwnerResponseDTO.setOwnerName(petOwner.getOwnerName());
        petOwnerResponseDTO.setAddress(petOwner.getAddress());
        petOwnerResponseDTO.setPhoneNumber(petOwner.getPhoneNumber());

        return petOwnerResponseDTO;
    }

    @Override
    public List<PetOwnerResponseDTO> findAllPetOwners() {
        List<PetOwner> petOwners = petOwnerRepository.findAll();
        return petOwners.stream().map( petOwner -> {
            PetOwnerResponseDTO petOwnerResponseDTO = new PetOwnerResponseDTO();
            petOwnerResponseDTO.setId(petOwner.getId());
            petOwnerResponseDTO.setOwnerName(petOwner.getOwnerName());
            petOwnerResponseDTO.setAddress(petOwner.getAddress());
            petOwnerResponseDTO.setPhoneNumber(petOwner.getPhoneNumber());
            return petOwnerResponseDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void deletePetOwnerById(Long id) {
        petOwnerRepository.deleteById(id);
    }

    @Override
    public PetOwner updatePetOwner(Long id, CreatePetOwnerRequestDTO createPetOwnerRequestDTO)
            throws PetOwnerNotFoundException {

        PetOwner existingPetOwner = petOwnerRepository.findById(id)
                .orElseThrow(() -> new PetOwnerNotFoundException("Pet Owner Not Found"));

        existingPetOwner.setOwnerName(createPetOwnerRequestDTO.getOwnerName());
        existingPetOwner.setAddress(createPetOwnerRequestDTO.getAddress());
        existingPetOwner.setPhoneNumber(createPetOwnerRequestDTO.getPhoneNumber());

        return petOwnerRepository.save(existingPetOwner);
    }
}
