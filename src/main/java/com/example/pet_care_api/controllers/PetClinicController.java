package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreatePetClinicRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetClinicResponseDTO;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.service.PetClinicService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinics")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class PetClinicController {


    private final PetClinicService petClinicService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetClinicResponseDTO createPetClinic(@Valid @RequestBody CreatePetClinicRequestDTO createPetClinicRequestDTO) {

        PetClinic petClinic = petClinicService.createPetClinic(createPetClinicRequestDTO);
        PetClinicResponseDTO petClinicResponseDTO = new PetClinicResponseDTO();
        petClinicResponseDTO.setId(petClinic.getId());
        petClinicResponseDTO.setClinicName(petClinic.getClinicName());
        petClinicResponseDTO.setAddress(createPetClinicRequestDTO.getAddress());
        petClinicResponseDTO.setPhoneNumber(petClinic.getPhoneNumber());
        return petClinicResponseDTO;
    }

    @GetMapping("/{pet-clinic-id}")
    public PetClinicResponseDTO findPetClinicById(@PathVariable("pet-clinic-id") Long id) {
        return petClinicService.findPetClinicById(id);
    }

    @GetMapping
    public List<PetClinicResponseDTO> findAllPetClinics() {
        return petClinicService.findAllPetClinics();
    }

    @DeleteMapping("/{pet-clinic-id}")
    public void deletePetClinicById(@PathVariable("pet-clinic-id") Long id) {
        petClinicService.deletePetClinicById(id);
    }

    @PutMapping("/{pet-clinic-id}")
    public PetClinicResponseDTO updatePetClinic(@PathVariable("pet-clinic-id") Long id, @RequestBody CreatePetClinicRequestDTO createPetClinicRequestDTO) {

        PetClinic petClinic = petClinicService.updatePetClinic(id, createPetClinicRequestDTO);
        PetClinicResponseDTO petClinicResponseDTO = new PetClinicResponseDTO();
        petClinicResponseDTO.setId(petClinic.getId());
        petClinicResponseDTO.setClinicName(petClinic.getClinicName());
        petClinicResponseDTO.setAddress(petClinic.getAddress());
        petClinicResponseDTO.setPhoneNumber(petClinic.getPhoneNumber());
        return petClinicResponseDTO;
    }
}
