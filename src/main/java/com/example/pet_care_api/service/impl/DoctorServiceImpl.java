package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreateDoctorRequestDTO;
import com.example.pet_care_api.controllers.dto.response.DoctorResponseDTO;
import com.example.pet_care_api.exceptions.DoctorNotFoundException;
import com.example.pet_care_api.models.Doctor;
import com.example.pet_care_api.repositories.DoctorRepository;
import com.example.pet_care_api.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;


    @Override
    public Doctor createDoctor(CreateDoctorRequestDTO createDoctorRequestDTO) {
        Doctor doctor = new Doctor();
        doctor.setDoctorName(createDoctorRequestDTO.getDoctorName());
        doctor.setPhoneNumber(createDoctorRequestDTO.getPhoneNumber());
        doctor.setQualifications(createDoctorRequestDTO.getQualifications());
        return doctorRepository.save(doctor);
    }

    @Override
    public DoctorResponseDTO findDoctorById(Long id) {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
        DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO();
        doctorResponseDTO.setId(doctor.getId());
        doctorResponseDTO.setDoctorName(doctor.getDoctorName());
        doctorResponseDTO.setPhoneNumber(doctor.getPhoneNumber());
        doctorResponseDTO.setQualifications(doctor.getQualifications());
        return doctorResponseDTO;
    }

    @Override
    public List<DoctorResponseDTO> findAllDoctors() {
        List<Doctor> doctors = doctorRepository.findAll();
        return doctors.stream().map(doctor -> {
            DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO();
            doctorResponseDTO.setId(doctor.getId());
            doctorResponseDTO.setDoctorName(doctor.getDoctorName());
            doctorResponseDTO.setPhoneNumber(doctor.getPhoneNumber());
            doctorResponseDTO.setQualifications(doctor.getQualifications());
            return doctorResponseDTO;
        }).collect(Collectors.toList());
    }
}
