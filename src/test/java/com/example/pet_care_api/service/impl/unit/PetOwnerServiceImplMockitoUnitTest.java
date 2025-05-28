package com.example.pet_care_api.service.impl.unit;

import com.example.pet_care_api.controllers.dto.request.CreatePetOwnerRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetOwnerResponseDTO;
import com.example.pet_care_api.exceptions.PetOwnerNotFoundException;
import com.example.pet_care_api.models.PetOwner;
import com.example.pet_care_api.repositories.PetOwnerRepository;
import com.example.pet_care_api.service.impl.PetOwnerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetOwnerServiceImplMockitoUnitTest {

    @InjectMocks
    private PetOwnerServiceImpl petOwnerService;

    @Mock
    private PetOwnerRepository petOwnerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePetOwner() {
        CreatePetOwnerRequestDTO dto = new CreatePetOwnerRequestDTO();
        dto.setOwnerName("John");
        dto.setAddress("123 Street");
        dto.setPhoneNumber("1234567890");

        PetOwner saved = new PetOwner();
        saved.setId(1L);
        saved.setOwnerName("John");
        saved.setAddress("123 Street");
        saved.setPhoneNumber("1234567890");

        when(petOwnerRepository.save(any())).thenReturn(saved);

        PetOwner result = petOwnerService.createPetOwner(dto);

        assertEquals("John", result.getOwnerName());
        assertNotNull(result.getId());
    }

    @Test
    void testFindPetOwnerById() {
        PetOwner petOwner = new PetOwner();
        petOwner.setId(1L);
        petOwner.setOwnerName("Alice");
        petOwner.setAddress("City Center");
        petOwner.setPhoneNumber("987654321");

        when(petOwnerRepository.findById(1L)).thenReturn(Optional.of(petOwner));

        PetOwnerResponseDTO result = petOwnerService.findPetOwnerById(1L);

        assertEquals("Alice", result.getOwnerName());
        assertEquals("City Center", result.getAddress());
        assertEquals("987654321", result.getPhoneNumber());
    }

    @Test
    void testFindPetOwnerById_NotFound() {
        when(petOwnerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PetOwnerNotFoundException.class, () -> {
            petOwnerService.findPetOwnerById(1L);
        });
    }

    @Test
    void testFindAllPetOwners() {
        PetOwner o1 = new PetOwner(); o1.setId(1L); o1.setOwnerName("A"); o1.setAddress("A St"); o1.setPhoneNumber("111");
        PetOwner o2 = new PetOwner(); o2.setId(2L); o2.setOwnerName("B"); o2.setAddress("B St"); o2.setPhoneNumber("222");

        when(petOwnerRepository.findAll()).thenReturn(Arrays.asList(o1, o2));

        List<PetOwnerResponseDTO> result = petOwnerService.findAllPetOwners();

        assertEquals(2, result.size());
    }

    @Test
    void testDeletePetOwnerById() {
        doNothing().when(petOwnerRepository).deleteById(1L);

        petOwnerService.deletePetOwnerById(1L);

        verify(petOwnerRepository, times(1)).deleteById(1L);
    }

    @Test
    void testUpdatePetOwner() {
        PetOwner existing = new PetOwner(); existing.setId(1L); existing.setOwnerName("Old");
        CreatePetOwnerRequestDTO dto = new CreatePetOwnerRequestDTO();
        dto.setOwnerName("New");
        dto.setAddress("New Address");
        dto.setPhoneNumber("999");

        when(petOwnerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(petOwnerRepository.save(any())).thenAnswer(i -> i.getArgument(0));

        PetOwner updated = petOwnerService.updatePetOwner(1L, dto);

        assertEquals("New", updated.getOwnerName());
        assertEquals("New Address", updated.getAddress());
        assertEquals("999", updated.getPhoneNumber());
    }

    @Test
    void testUpdatePetOwner_NotFound() {
        CreatePetOwnerRequestDTO dto = new CreatePetOwnerRequestDTO();
        dto.setOwnerName("X");

        when(petOwnerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(PetOwnerNotFoundException.class, () -> {
            petOwnerService.updatePetOwner(1L, dto);
        });
    }
}
