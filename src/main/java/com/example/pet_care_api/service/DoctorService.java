package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateDoctorRequestDTO;
import com.example.pet_care_api.controllers.dto.response.DoctorResponseDTO;
import com.example.pet_care_api.exceptions.DoctorNotFoundException;
import com.example.pet_care_api.exceptions.PetClinicNotFoundException;
import com.example.pet_care_api.models.Doctor;

import java.util.List;

public interface DoctorService {

    Doctor createDoctor(Long petClinicId, CreateDoctorRequestDTO createDoctorRequestDTO) throws PetClinicNotFoundException;

    DoctorResponseDTO findDoctorById(Long id) throws DoctorNotFoundException;

    List<DoctorResponseDTO> findAllDoctors();

    void deleteDoctorById(Long id);

    Doctor updateDoctor(Long id,CreateDoctorRequestDTO createDoctorRequestDTO) throws DoctorNotFoundException;
}
