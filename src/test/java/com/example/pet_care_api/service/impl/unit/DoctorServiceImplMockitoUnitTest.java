package com.example.pet_care_api.service.impl.unit;

import com.example.pet_care_api.controllers.dto.request.CreateDoctorRequestDTO;
import com.example.pet_care_api.controllers.dto.response.DoctorResponseDTO;
import com.example.pet_care_api.exceptions.DoctorNotFoundException;
import com.example.pet_care_api.exceptions.PetClinicNotFoundException;
import com.example.pet_care_api.models.Doctor;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.repositories.DoctorRepository;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DoctorServiceImplMockitoUnitTest {

    @Mock private DoctorRepository doctorRepository;
    @Mock private PetClinicRepository petClinicRepository;
    @InjectMocks private DoctorServiceImpl doctorService;

    private PetClinic petClinic;
    private Doctor doctor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        petClinic = new PetClinic();
        petClinic.setId(1L);

        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setDoctorName("Dr. Jane");
        doctor.setPhoneNumber(123456789);
        doctor.setQualifications("MBBS");
        doctor.setPetClinic(petClinic);
    }

    @Test
    void createDoctor_success() {
        CreateDoctorRequestDTO dto = new CreateDoctorRequestDTO();
        dto.setDoctorName("Dr. Jane");
        dto.setPhoneNumber(123456789);
        dto.setQualifications("MBBS");

        when(petClinicRepository.findById(1L)).thenReturn(Optional.of(petClinic));
        when(doctorRepository.save(any())).thenReturn(doctor);

        Doctor created = doctorService.createDoctor(1L, dto);

        assertNotNull(created);
        assertEquals("Dr. Jane", created.getDoctorName());
    }

    @Test
    void createDoctor_petClinicNotFound() {
        CreateDoctorRequestDTO dto = new CreateDoctorRequestDTO();
        when(petClinicRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(PetClinicNotFoundException.class, () -> doctorService.createDoctor(2L, dto));
    }

    @Test
    void findDoctorById_success() throws DoctorNotFoundException {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        DoctorResponseDTO dto = doctorService.findDoctorById(1L);
        assertEquals("Dr. Jane", dto.getDoctorName());
    }

    @Test
    void findDoctorById_notFound() {
        when(doctorRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(DoctorNotFoundException.class, () -> doctorService.findDoctorById(2L));
    }

    @Test
    void findAllDoctors() {
        when(doctorRepository.findAll()).thenReturn(List.of(doctor));

        var doctors = doctorService.findAllDoctors();
        assertEquals(1, doctors.size());
    }

    @Test
    void deleteDoctorById() {
        doNothing().when(doctorRepository).deleteById(1L);
        doctorService.deleteDoctorById(1L);
        verify(doctorRepository, times(1)).deleteById(1L);
    }

    @Test
    void updateDoctor_success() throws DoctorNotFoundException {
        CreateDoctorRequestDTO dto = new CreateDoctorRequestDTO();
        dto.setDoctorName("Dr. John");
        dto.setPhoneNumber(987654321);
        dto.setQualifications("DVM");

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorRepository.save(any())).thenReturn(doctor);

        Doctor updated = doctorService.updateDoctor(1L, dto);

        assertEquals("Dr. John", updated.getDoctorName());
        assertEquals(987654321, updated.getPhoneNumber());
    }
}
