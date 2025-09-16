package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreateDoctorRequestDTO;
import com.example.pet_care_api.controllers.dto.response.DoctorResponseDTO;
import com.example.pet_care_api.exceptions.DoctorNotFoundException;
import com.example.pet_care_api.exceptions.PetClinicNotFoundException;
import com.example.pet_care_api.models.Doctor;
import com.example.pet_care_api.models.PetClinic;
import com.example.pet_care_api.repositories.DoctorRepository;
import com.example.pet_care_api.repositories.PetClinicRepository;
import com.example.pet_care_api.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PetClinicRepository petClinicRepository;


    @Override
    public Doctor createDoctor(Long petClinicId,CreateDoctorRequestDTO createDoctorRequestDTO) throws PetClinicNotFoundException {

        PetClinic petClinic = petClinicRepository.findById(petClinicId)
                .orElseThrow(() -> new PetClinicNotFoundException("pet clinic not found"));

        Doctor doctor = new Doctor();
        doctor.setDoctorName(createDoctorRequestDTO.getDoctorName());
        doctor.setPhoneNumber(createDoctorRequestDTO.getPhoneNumber());
        doctor.setQualifications(createDoctorRequestDTO.getQualifications());
        doctor.setPetClinic(petClinic);
        return doctorRepository.save(doctor);
    }

    @Override
    public DoctorResponseDTO findDoctorById(Long id) throws DoctorNotFoundException {

        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
        DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO();
        doctorResponseDTO.setId(doctor.getId());
        doctorResponseDTO.setDoctorName(doctor.getDoctorName());
        doctorResponseDTO.setPhoneNumber(doctor.getPhoneNumber());
        doctorResponseDTO.setQualifications(doctor.getQualifications());
        doctorResponseDTO.setPetClinicId(doctor.getPetClinic().getId());
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
            doctorResponseDTO.setPetClinicId(doctor.getPetClinic().getId());
            return doctorResponseDTO;
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteDoctorById(Long id) throws DoctorNotFoundException {
        if (!doctorRepository.existsById(id)) {
            throw new DoctorNotFoundException("Doctor with ID " + id + " not found.");
        }
        doctorRepository.deleteById(id);
    }

    @Override
    public Doctor updateDoctor(Long id, CreateDoctorRequestDTO createDoctorRequestDTO) throws DoctorNotFoundException {

        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor not found"));
        existingDoctor.setDoctorName(createDoctorRequestDTO.getDoctorName());
        existingDoctor.setPhoneNumber(createDoctorRequestDTO.getPhoneNumber());
        existingDoctor.setQualifications(createDoctorRequestDTO.getQualifications());

        return doctorRepository.save(existingDoctor);
    }
}
