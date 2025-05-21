package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreateDoctorRequestDTO;
import com.example.pet_care_api.models.Doctor;
import com.example.pet_care_api.repositories.DoctorRepository;
import com.example.pet_care_api.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
