package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreatePetOwnerRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetOwnerResponseDTO;
import com.example.pet_care_api.models.PetOwner;
import com.example.pet_care_api.service.PetOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
public class PetOwnerController {

    @Autowired
    private PetOwnerService petOwnerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetOwnerResponseDTO createPetOwner(@RequestBody CreatePetOwnerRequestDTO createPetOwnerRequestDTO) {

        PetOwner petOwner = petOwnerService.createPetOwner(createPetOwnerRequestDTO);

        PetOwnerResponseDTO petOwnerResponseDTO = new PetOwnerResponseDTO();
        petOwnerResponseDTO.setId(petOwner.getId());
        petOwnerResponseDTO.setOwnerName(petOwner.getOwnerName());
        petOwnerResponseDTO.setAddress(petOwner.getAddress());
        petOwnerResponseDTO.setPhoneNumber(petOwner.getPhoneNumber());
        return petOwnerResponseDTO;
    }

    @GetMapping("/{pet-owner-id}")
    public PetOwnerResponseDTO getPetOwnerById(@PathVariable("pet-owner-id") Long petOwnerId) {
        return petOwnerService.findPetOwnerById(petOwnerId);
    }

    @GetMapping
    public List<PetOwnerResponseDTO> getAllPetOwners() {
        return petOwnerService.findAllPetOwners();
    }

    @DeleteMapping("/{pet-owner-id}")
    public void deletePetOwnerById(@PathVariable("pet-owner-id") Long petOwnerId) {
        petOwnerService.deletePetOwnerById(petOwnerId);
    }

    @PutMapping("/{pet-owner-id}")
    public PetOwnerResponseDTO updatePetOwner(@PathVariable("pet-owner-id") Long id, @RequestBody CreatePetOwnerRequestDTO createPetOwnerRequestDTO) {

        PetOwner petOwner = petOwnerService.updatePetOwner(id, createPetOwnerRequestDTO);
        PetOwnerResponseDTO petOwnerResponseDTO = new PetOwnerResponseDTO();

        petOwnerResponseDTO.setOwnerName(petOwner.getOwnerName());
        petOwnerResponseDTO.setAddress(petOwner.getAddress());
        petOwnerResponseDTO.setPhoneNumber(petOwner.getPhoneNumber());

        return petOwnerResponseDTO;
    }
}
