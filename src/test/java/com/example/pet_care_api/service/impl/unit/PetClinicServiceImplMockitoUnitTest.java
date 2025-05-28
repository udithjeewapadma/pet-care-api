package com.example.pet_care_api.service.impl.unit;

import com.example.pet_care_api.controllers.dto.request.CreatePetClinicRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetClinicResponseDTO;
import com.example.pet_care_api.exceptions.PetClinicNotFoundException;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.service.impl.PetClinicServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PetClinicServiceImplMockitoUnitTest {

    @InjectMocks
    private PetClinicServiceImpl petClinicService;

    @Mock
    private PetClinicRepository petClinicRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createPetClinic_success() {
        CreatePetClinicRequestDTO dto = new CreatePetClinicRequestDTO();
        dto.setClinicName("Animal Wellness");
        dto.setAddress("123 Main St");
        dto.setPhoneNumber("123456789");

        PetClinic saved = new PetClinic();
        saved.setId(1L);
        saved.setClinicName(dto.getClinicName());
        saved.setAddress(dto.getAddress());
        saved.setPhoneNumber(dto.getPhoneNumber());

        when(petClinicRepository.save(any())).thenReturn(saved);

        PetClinic result = petClinicService.createPetClinic(dto);
        assertEquals("Animal Wellness", result.getClinicName());
        assertEquals("123 Main St", result.getAddress());
        assertEquals("123456789", result.getPhoneNumber());
    }

    @Test
    void findPetClinicById_success() throws PetClinicNotFoundException {
        PetClinic petClinic = new PetClinic();
        petClinic.setId(1L);
        petClinic.setClinicName("Clinic");
        petClinic.setAddress("Somewhere");
        petClinic.setPhoneNumber("111111111");

        when(petClinicRepository.findById(1L)).thenReturn(Optional.of(petClinic));

        PetClinicResponseDTO response = petClinicService.findPetClinicById(1L);
        assertEquals("Clinic", response.getClinicName());
    }

    @Test
    void findPetClinicById_notFound() {
        when(petClinicRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(PetClinicNotFoundException.class, () -> petClinicService.findPetClinicById(1L));
    }

    @Test
    void findAllPetClinics_success() {
        PetClinic p1 = new PetClinic();
        p1.setId(1L);
        p1.setClinicName("A");

        PetClinic p2 = new PetClinic();
        p2.setId(2L);
        p2.setClinicName("B");

        when(petClinicRepository.findAll()).thenReturn(Arrays.asList(p1, p2));

        List<PetClinicResponseDTO> result = petClinicService.findAllPetClinics();
        assertEquals(2, result.size());
    }

    @Test
    void updatePetClinic_success() throws PetClinicNotFoundException {
        CreatePetClinicRequestDTO dto = new CreatePetClinicRequestDTO();
        dto.setClinicName("Updated Clinic");
        dto.setAddress("New Address");
        dto.setPhoneNumber("999999999");

        PetClinic existing = new PetClinic();
        existing.setId(1L);
        existing.setClinicName("Old Clinic");

        when(petClinicRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(petClinicRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        PetClinic updated = petClinicService.updatePetClinic(1L, dto);

        assertEquals("Updated Clinic", updated.getClinicName());
        assertEquals("New Address", updated.getAddress());
        assertEquals("999999999", updated.getPhoneNumber());
    }

    @Test
    void deletePetClinicById_success() {
        petClinicService.deletePetClinicById(1L);
        verify(petClinicRepository, times(1)).deleteById(1L);
    }
}
