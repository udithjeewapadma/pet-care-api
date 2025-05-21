package com.example.pet_care_api.service.impl;

import com.cloudinary.Cloudinary;
import com.example.pet_care_api.controllers.dto.request.CreatePetRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetResponseDTO;
import com.example.pet_care_api.exceptions.DoctorNotFoundException;
import com.example.pet_care_api.exceptions.PetCategoryNotFoundException;
import com.example.pet_care_api.exceptions.PetNotFoundException;
import com.example.pet_care_api.exceptions.PetOwnerNotFoundException;
import com.example.pet_care_api.models.Doctor;
import com.example.pet_care_api.models.Pet;
import com.example.pet_care_api.models.PetCategory;
import com.example.pet_care_api.models.PetOwner;
import com.example.pet_care_api.repositories.DoctorRepository;
import com.example.pet_care_api.repositories.PetCategoryRepository;
import com.example.pet_care_api.repositories.PetOwnerRepository;
import com.example.pet_care_api.repositories.PetRepository;
import com.example.pet_care_api.service.PetService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    @Autowired
    private PetCategoryRepository petCategoryRepository;

    @Autowired
    private Cloudinary cloudinary;


    @Override
    public PetResponseDTO createPet(Long doctorId,
                         Long petCategoryId,
                         Long petOwnerId, CreatePetRequestDTO createPetRequestDTO) throws IOException {

        PetCategory petCategory = petCategoryRepository.findById(petCategoryId)
                .orElseThrow(() -> new PetCategoryNotFoundException("Pet Category Not Found"));

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor Not Found"));

        PetOwner petOwner = petOwnerRepository.findById(petOwnerId)
                .orElseThrow(() -> new PetOwnerNotFoundException("Pet Owner Not Found"));

        Pet pet = new Pet();
        pet.setPetName(createPetRequestDTO.getPetName());
        pet.setGender(createPetRequestDTO.getGender());
        pet.setBirthDate(createPetRequestDTO.getBirthDate());
        pet.setPetCategory(petCategory);
        pet.setDoctor(doctor);
        pet.setPetOwner(petOwner);

        List<String> imageUrls = new ArrayList<>();
        if (createPetRequestDTO.getImageFiles() != null) {
            for (MultipartFile file : createPetRequestDTO.getImageFiles()) {
                String imageUrl = cloudinary.uploader()
                        .upload(file.getBytes(),
                                Map.of("public_id", UUID.randomUUID().toString()))
                        .get("url")
                        .toString();
                imageUrls.add(imageUrl);
            }
        }
        pet.setImageUrl(imageUrls);


        Pet savedPet = petRepository.save(pet);

        PetResponseDTO responseDTO = new PetResponseDTO();
        responseDTO.setId(savedPet.getId());
        responseDTO.setPetName(savedPet.getPetName());
        responseDTO.setGender(savedPet.getGender());
        responseDTO.setBirthDate(savedPet.getBirthDate());
        responseDTO.setImageUrls(savedPet.getImageUrl());

        return responseDTO;
    }


    @Override
    @Transactional
    public PetResponseDTO getPetById(Long petId) {

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new PetNotFoundException("Pet Not Found"));

        return new PetResponseDTO(
                pet.getId(),
                pet.getPetName(),
                pet.getGender(),
                pet.getBirthDate(),
                pet.getImageUrl(),
                pet.getPetOwner().getId(),
                pet.getDoctor().getId(),
                pet.getPetCategory().getId()
        );

    }

    @Override
    public List<PetResponseDTO> findAllPets() {
        List<Pet> pets = petRepository.findAll();
        return pets.stream().map(pet -> new PetResponseDTO(
                pet.getId(),
                pet.getPetName(),
                pet.getGender(),
                pet.getBirthDate(),
                pet.getImageUrl(),
                pet.getPetOwner().getId(),
                pet.getDoctor().getId(),
                pet.getPetCategory().getId()
        )).collect(Collectors.toList());
    }

    @Override
    public void deletePetById(Long petId) {
        petRepository.deleteById(petId);
    }

    @Override
    @Transactional
    public PetResponseDTO updatePet(Long id, CreatePetRequestDTO request) throws IOException {
        Pet pet = petRepository.findById(id).orElseThrow(() -> new PetNotFoundException("Pet not found"));

        pet.setPetName(request.getPetName());
        pet.setGender(request.getGender());
        pet.setBirthDate(request.getBirthDate());

        List<String> imageUrls = new ArrayList<>();
        if (request.getImageFiles() != null) {
            for (MultipartFile file : request.getImageFiles()) {
                String imageUrl = cloudinary.uploader()
                        .upload(file.getBytes(), Map.of("public_id", UUID.randomUUID().toString()))
                        .get("url").toString();
                imageUrls.add(imageUrl);
            }
            pet.setImageUrl(imageUrls);
        }

        Pet updatedPet = petRepository.save(pet);

        return new PetResponseDTO(
                updatedPet.getId(),
                updatedPet.getPetName(),
                updatedPet.getGender(),
                updatedPet.getBirthDate(),
                updatedPet.getImageUrl(),
                updatedPet.getPetOwner().getId(),
                updatedPet.getDoctor().getId(),
                updatedPet.getPetCategory().getId()
        );
    }


}
