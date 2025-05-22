package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreatePetClinicRequestDTO;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.service.PetClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
