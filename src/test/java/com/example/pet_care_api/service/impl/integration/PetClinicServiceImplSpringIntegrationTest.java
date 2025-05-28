package com.example.pet_care_api.service.impl.integration;

import com.example.pet_care_api.controllers.dto.request.CreatePetClinicRequestDTO;
import com.example.pet_care_api.exceptions.PetClinicNotFoundException;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.service.PetClinicService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetClinicServiceImplSpringIntegrationTest {

    @Autowired
    private PetClinicService petClinicService;

    @Autowired
    private PetClinicRepository petClinicRepository;

    private static Long clinicId;

    @Test
    @Order(1)
    void createPetClinic_success() {
        CreatePetClinicRequestDTO dto = new CreatePetClinicRequestDTO();
        dto.setClinicName("Integration Test Clinic");
        dto.setAddress("456 Real Ave");
        dto.setPhoneNumber("888888888");

        PetClinic saved = petClinicService.createPetClinic(dto);
        clinicId = saved.getId();

        assertNotNull(saved.getId());
        assertEquals("Integration Test Clinic", saved.getClinicName());
    }

    @Test
    @Order(2)
    void findPetClinicById_success() throws PetClinicNotFoundException {
        var response = petClinicService.findPetClinicById(clinicId);
        assertEquals("Integration Test Clinic", response.getClinicName());
    }

    @Test
    @Order(3)
    void updatePetClinic_success() throws PetClinicNotFoundException {
        CreatePetClinicRequestDTO update = new CreatePetClinicRequestDTO();
        update.setClinicName("Updated Clinic");
        update.setAddress("Updated Address");
        update.setPhoneNumber("777777777");

        PetClinic updated = petClinicService.updatePetClinic(clinicId, update);
        assertEquals("Updated Clinic", updated.getClinicName());
    }

    @Test
    @Order(4)
    void deletePetClinicById_success() {
        petClinicService.deletePetClinicById(clinicId);
        assertFalse(petClinicRepository.findById(clinicId).isPresent());
    }
}
