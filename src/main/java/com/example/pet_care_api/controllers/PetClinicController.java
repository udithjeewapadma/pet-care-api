package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreatePetClinicRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetClinicResponseDTO;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.service.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinics")
public class PetClinicController {

    @Autowired
    private PetClinicService petClinicService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private PetClinicResponseDTO createPetClinic(@RequestBody CreatePetClinicRequestDTO createPetClinicRequestDTO) {

        PetClinic petClinic = petClinicService.createPetClinic(createPetClinicRequestDTO);
        PetClinicResponseDTO petClinicResponseDTO = new PetClinicResponseDTO();
        petClinicResponseDTO.setId(petClinic.getId());
        petClinicResponseDTO.setClinicName(petClinic.getClinicName());
        petClinicResponseDTO.setAddress(createPetClinicRequestDTO.getAddress());
        petClinicResponseDTO.setPhoneNumber(petClinic.getPhoneNumber());
        return petClinicResponseDTO;
    }

    @GetMapping("/{pet-clinic-id}")
    private PetClinicResponseDTO findPetClinicById(@PathVariable("pet-clinic-id") Long id) {
        return petClinicService.findPetClinicById(id);
    }

    @GetMapping
    private List<PetClinicResponseDTO> findAllPetClinics() {
        return petClinicService.findAllPetClinics();
    }
}
