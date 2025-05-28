package com.example.pet_care_api.service.impl.integration;

import com.example.pet_care_api.controllers.dto.request.CreateDoctorRequestDTO;
import com.example.pet_care_api.controllers.dto.response.DoctorResponseDTO;
import com.example.pet_care_api.exceptions.DoctorNotFoundException;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.repositories.DoctorRepository;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.service.DoctorService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Transactional
class DoctorServiceImplSpringIntegrationTest {

    @Autowired private DoctorService doctorService;
    @Autowired private PetClinicRepository petClinicRepository;
    @Autowired private DoctorRepository doctorRepository;

    private static Long doctorId;
    private static Long petClinicId;

    @BeforeEach
    void setup() {
        PetClinic clinic = new PetClinic();
        clinic.setClinicName("Happy Pets Clinic");
        petClinicId = petClinicRepository.save(clinic).getId();
    }

    @Test
    @Order(1)
    void testCreateDoctor() {
        CreateDoctorRequestDTO dto = new CreateDoctorRequestDTO();
        dto.setDoctorName("Dr. Emily");
        dto.setPhoneNumber(987654321);
        dto.setQualifications("MBBS");

        var doctor = doctorService.createDoctor(petClinicId, dto);
        assertNotNull(doctor.getId());

        doctorId = doctor.getId();
    }

    @Test
    @Order(2)
    void testFindDoctorById() throws DoctorNotFoundException {
        testCreateDoctor(); // Ensure doctor exists
        DoctorResponseDTO found = doctorService.findDoctorById(doctorId);
        assertEquals("Dr. Emily", found.getDoctorName());
    }

    @Test
    @Order(3)
    void testFindAllDoctors() {
        testCreateDoctor();
        List<DoctorResponseDTO> doctors = doctorService.findAllDoctors();
        assertTrue(doctors.stream().anyMatch(d -> d.getDoctorName().equals("Dr. Emily")));
    }

    @Test
    @Order(4)
    void testUpdateDoctor() throws DoctorNotFoundException {
        testCreateDoctor();
        CreateDoctorRequestDTO updateDTO = new CreateDoctorRequestDTO();
        updateDTO.setDoctorName("Dr. Updated");
        updateDTO.setPhoneNumber(123123123);
        updateDTO.setQualifications("PhD");

        var updated = doctorService.updateDoctor(doctorId, updateDTO);
        assertEquals("Dr. Updated", updated.getDoctorName());
    }

    @Test
    @Order(5)
    void testDeleteDoctorById() throws DoctorNotFoundException {
        testCreateDoctor();
        doctorService.deleteDoctorById(doctorId);
        assertThrows(DoctorNotFoundException.class, () -> doctorService.findDoctorById(doctorId));
    }
}

