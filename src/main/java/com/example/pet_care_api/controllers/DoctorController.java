package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreateDoctorRequestDTO;
import com.example.pet_care_api.controllers.dto.response.DoctorResponseDTO;
import com.example.pet_care_api.models.Doctor;
import com.example.pet_care_api.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private DoctorResponseDTO createDoctor(@RequestParam Long petClinicId,
                                           @RequestBody CreateDoctorRequestDTO createDoctorRequestDTO) {
        Doctor doctor = doctorService.createDoctor(petClinicId,createDoctorRequestDTO);

        DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO();
        doctorResponseDTO.setId(doctor.getId());
        doctorResponseDTO.setDoctorName(doctor.getDoctorName());
        doctorResponseDTO.setPhoneNumber(doctor.getPhoneNumber());
        doctorResponseDTO.setQualifications(doctor.getQualifications());
        doctorResponseDTO.setPetClinicId(doctor.getPetClinic().getId());

        return doctorResponseDTO;
    }

    @GetMapping("/{doctor-id}")
    private DoctorResponseDTO getDoctorById(@PathVariable("doctor-id") Long doctorId) {
        return doctorService.findDoctorById(doctorId);
    }

    @GetMapping
    private List<DoctorResponseDTO> findAllDoctors() {
        return doctorService.findAllDoctors();
    }

    @DeleteMapping("/{doctor-id}")
    private void deleteDoctorById(@PathVariable("doctor-id") Long doctorId) {
        doctorService.deleteDoctorById(doctorId);
    }

    @PutMapping("/{doctor-id}")
    private DoctorResponseDTO updateDoctor(@PathVariable("doctor-id") Long id,
                                           @RequestBody CreateDoctorRequestDTO createDoctorRequestDTO) {
        Doctor doctor = doctorService.updateDoctor(id,createDoctorRequestDTO);

        DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO();
        doctorResponseDTO.setId(doctor.getId());
        doctorResponseDTO.setDoctorName(doctor.getDoctorName());
        doctorResponseDTO.setPhoneNumber(doctor.getPhoneNumber());
        doctorResponseDTO.setQualifications(doctor.getQualifications());
        return doctorResponseDTO;

    }
}
