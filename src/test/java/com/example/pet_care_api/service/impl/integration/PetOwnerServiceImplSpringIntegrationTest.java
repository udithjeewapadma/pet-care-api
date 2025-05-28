package com.example.pet_care_api.service.impl.integration;

import com.example.pet_care_api.controllers.dto.request.CreatePetOwnerRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetOwnerResponseDTO;
import com.example.pet_care_api.exceptions.PetOwnerNotFoundException;
import com.example.pet_care_api.models.PetOwner;
import com.example.pet_care_api.repositories.PetOwnerRepository;
import com.example.pet_care_api.service.PetOwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PetOwnerServiceImplSpringIntegrationTest {

    @Autowired
    private PetOwnerService petOwnerService;

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    @BeforeEach
    void clearRepo() {
        petOwnerRepository.deleteAll();
    }

    @Test
    void testCreateAndFindPetOwner() {
        CreatePetOwnerRequestDTO dto = new CreatePetOwnerRequestDTO();
        dto.setOwnerName("Tom");
        dto.setAddress("Mars Street");
        dto.setPhoneNumber("12345");

        PetOwner created = petOwnerService.createPetOwner(dto);

        PetOwnerResponseDTO found = petOwnerService.findPetOwnerById(created.getId());

        assertEquals("Tom", found.getOwnerName());
    }

    @Test
    void testFindAllPetOwners() {
        CreatePetOwnerRequestDTO d1 = new CreatePetOwnerRequestDTO(); d1.setOwnerName("A");
        CreatePetOwnerRequestDTO d2 = new CreatePetOwnerRequestDTO(); d2.setOwnerName("B");

        petOwnerService.createPetOwner(d1);
        petOwnerService.createPetOwner(d2);

        List<PetOwnerResponseDTO> owners = petOwnerService.findAllPetOwners();

        assertEquals(2, owners.size());
    }

    @Test
    void testUpdatePetOwner() {
        CreatePetOwnerRequestDTO create = new CreatePetOwnerRequestDTO();
        create.setOwnerName("Bob");
        create.setAddress("Old Addr");
        create.setPhoneNumber("111");

        PetOwner created = petOwnerService.createPetOwner(create);

        CreatePetOwnerRequestDTO update = new CreatePetOwnerRequestDTO();
        update.setOwnerName("Bob Updated");
        update.setAddress("New Addr");
        update.setPhoneNumber("999");

        PetOwner updated = petOwnerService.updatePetOwner(created.getId(), update);

        assertEquals("Bob Updated", updated.getOwnerName());
        assertEquals("New Addr", updated.getAddress());
        assertEquals("999", updated.getPhoneNumber());
    }

    @Test
    void testDeletePetOwner() {
        CreatePetOwnerRequestDTO dto = new CreatePetOwnerRequestDTO();
        dto.setOwnerName("Delete Me");
        PetOwner owner = petOwnerService.createPetOwner(dto);

        petOwnerService.deletePetOwnerById(owner.getId());

        assertThrows(PetOwnerNotFoundException.class, () -> {
            petOwnerService.findPetOwnerById(owner.getId());
        });
    }
}
