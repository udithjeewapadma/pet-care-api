package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreatePetRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetResponseDTO;
import com.example.pet_care_api.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetResponseDTO createPet(@RequestParam Long doctorId,
                                    @RequestParam Long petCategoryId,
                                    @RequestParam Long petOwnerId,
                                    @ModelAttribute CreatePetRequestDTO createPetRequestDTO) throws IOException {

        return petService.createPet(doctorId, petCategoryId, petOwnerId, createPetRequestDTO);

    }

    @GetMapping("/{pet-id}")
    public PetResponseDTO getPetById(@PathVariable("pet-id") Long petId) {
        return petService.getPetById(petId);
    }


}
