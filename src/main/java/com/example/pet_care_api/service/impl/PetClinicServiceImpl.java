package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreatePetClinicRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetClinicResponseDTO;
import com.example.pet_care_api.exceptions.PetClinicNotFoundException;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.service.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PetClinicServiceImpl implements PetClinicService {
    @Autowired
    private PetClinicRepository petClinicRepository;

    @Override
    public PetClinic createPetClinic(CreatePetClinicRequestDTO createPetClinicRequestDTO) {

        PetClinic petClinic = new PetClinic();
        petClinic.setClinicName(createPetClinicRequestDTO.getClinicName());
        petClinic.setAddress(createPetClinicRequestDTO.getAddress());
        petClinic.setPhoneNumber(createPetClinicRequestDTO.getPhoneNumber());

        return petClinicRepository.save(petClinic);

    }

    @Override
    public PetClinicResponseDTO findPetClinicById(Long id) throws PetClinicNotFoundException {

        PetClinic petClinic = petClinicRepository.findById(id)
                .orElseThrow(() -> new PetClinicNotFoundException("pet clinic not found"));
        PetClinicResponseDTO petClinicResponseDTO = new PetClinicResponseDTO();
        petClinicResponseDTO.setId(petClinic.getId());
        petClinicResponseDTO.setClinicName(petClinic.getClinicName());
        petClinicResponseDTO.setAddress(petClinic.getAddress());
        petClinicResponseDTO.setPhoneNumber(petClinic.getPhoneNumber());
        return petClinicResponseDTO;
    }

    @Override
    public List<PetClinicResponseDTO> findAllPetClinics() {
        List<PetClinic> petClinics = petClinicRepository.findAll();
        return petClinics.stream().map(petClinic -> {
            PetClinicResponseDTO petClinicResponseDTO = new PetClinicResponseDTO();
            petClinicResponseDTO.setId(petClinic.getId());
            petClinicResponseDTO.setClinicName(petClinic.getClinicName());
            petClinicResponseDTO.setAddress(petClinic.getAddress());
            petClinicResponseDTO.setPhoneNumber(petClinic.getPhoneNumber());
            return petClinicResponseDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void deletePetClinicById(Long id) throws PetClinicNotFoundException {
        if(!petClinicRepository.existsById(id)) {
            throw new PetClinicNotFoundException("Pet Clinic with ID " + id + " not found.");
        }
        petClinicRepository.deleteById(id);
    }

    @Override
    public PetClinic updatePetClinic(Long id, CreatePetClinicRequestDTO createPetClinicRequestDTO)
            throws PetClinicNotFoundException {

        PetClinic existingPetClinic = petClinicRepository.findById(id)
                .orElseThrow(() -> new PetClinicNotFoundException("pet clinic not found"));

        existingPetClinic.setClinicName(createPetClinicRequestDTO.getClinicName());
        existingPetClinic.setAddress(createPetClinicRequestDTO.getAddress());
        existingPetClinic.setPhoneNumber(createPetClinicRequestDTO.getPhoneNumber());

        return petClinicRepository.save(existingPetClinic);
    }
}
