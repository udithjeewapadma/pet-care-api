package com.example.pet_care_api.service.impl.unit;

import com.example.pet_care_api.controllers.dto.request.CreateDealerRequestDTO;
import com.example.pet_care_api.exceptions.DealerNotFoundException;
import com.example.pet_care_api.exceptions.PetClinicNotFoundException;
import com.example.pet_care_api.models.Dealer;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.repositories.DealerRepository;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.service.impl.DealerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DealerServiceImplMockitoUnitTest {

    @Mock
    private DealerRepository dealerRepository;

    @Mock
    private PetClinicRepository petClinicRepository;

    @InjectMocks
    private DealerServiceImpl dealerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createDealer_success() {
        CreateDealerRequestDTO dto = new CreateDealerRequestDTO();
        dto.setDealerName("Dealer One");
        dto.setPhoneNumber("1234567890");
        dto.setEmail("dealer@example.com");
        dto.setItemName("Medicine");
        dto.setPetClinicIds(List.of(1L));

        PetClinic petClinic = new PetClinic();
        petClinic.setId(1L);

        when(petClinicRepository.findAllById(dto.getPetClinicIds())).thenReturn(List.of(petClinic));
        when(dealerRepository.save(any(Dealer.class))).thenAnswer(i -> i.getArgument(0));

        Dealer result = dealerService.createDealer(dto);

        assertEquals("Dealer One", result.getDealerName());
        verify(dealerRepository, times(1)).save(any(Dealer.class));
    }

    @Test
    void createDealer_throwsPetClinicNotFound() {
        CreateDealerRequestDTO dto = new CreateDealerRequestDTO();
        dto.setPetClinicIds(List.of(1L));

        when(petClinicRepository.findAllById(dto.getPetClinicIds())).thenReturn(List.of());

        assertThrows(PetClinicNotFoundException.class, () -> dealerService.createDealer(dto));
    }

    @Test
    void findDealerById_success() throws DealerNotFoundException {
        Dealer dealer = new Dealer();
        dealer.setId(1L);
        dealer.setDealerName("Dealer A");
        dealer.setPhoneNumber("1234567890");
        dealer.setEmail("dealer@example.com");
        dealer.setItemName("Food");

        PetClinic clinic = new PetClinic();
        clinic.setId(1L);
        clinic.setClinicName("Happy Pets");

        dealer.setPetClinics(List.of(clinic));

        when(dealerRepository.findById(1L)).thenReturn(Optional.of(dealer));

        var result = dealerService.findDealerById(1L);

        assertEquals("Dealer A", result.getDealerName());
        assertEquals("Happy Pets", result.getPetClinics().get(0).getPetClinicName());
    }

    @Test
    void findDealerById_notFound() {
        when(dealerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(DealerNotFoundException.class, () -> dealerService.findDealerById(1L));
    }
}
