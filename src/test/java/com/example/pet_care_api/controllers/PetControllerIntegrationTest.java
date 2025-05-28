package com.example.pet_care_api.controllers;

import com.example.pet_care_api.models.Doctor;
import com.example.pet_care_api.models.Pet;
import com.example.pet_care_api.models.PetCategory;
import com.example.pet_care_api.models.PetOwner;
import com.example.pet_care_api.repositories.DoctorRepository;
import com.example.pet_care_api.repositories.PetCategoryRepository;
import com.example.pet_care_api.repositories.PetOwnerRepository;
import com.example.pet_care_api.repositories.PetRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class PetControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PetCategoryRepository petCategoryRepository;

    @Autowired
    private PetOwnerRepository petOwnerRepository;

    private Long existingPetId;
    private Long doctorId;
    private Long petCategoryId;
    private Long petOwnerId;

    @BeforeEach
    public void setUp() {
        // Save related entities to DB and get their IDs
        Doctor doctor = new Doctor();
        doctor.setDoctorName("Dr. Smith");
        doctor = doctorRepository.save(doctor);
        doctorId = doctor.getId();

        PetCategory category = new PetCategory();
        category.setCategoryName("Dog");
        category = petCategoryRepository.save(category);
        petCategoryId = category.getId();

        PetOwner owner = new PetOwner();
        owner.setOwnerName("John Doe");
        owner = petOwnerRepository.save(owner);
        petOwnerId = owner.getId();

        // Create and save a pet
        Pet pet = new Pet();
        pet.setPetName("OriginalName");
        pet.setDoctor(doctor);
        pet.setPetCategory(category);
        pet.setPetOwner(owner);
        pet = petRepository.save(pet);

        existingPetId = pet.getId();
    }

    @Test
    public void testCreatePet() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile(
                "image",
                "pet.jpg",
                MediaType.IMAGE_JPEG_VALUE,
                "FakeImageContent".getBytes(StandardCharsets.UTF_8));

        mockMvc.perform(multipart("/pets")
                        .file(imageFile)
                        .param("petName", "Buddy")   // <-- send text field as param, NOT as a multipart file
                        .param("age", "3")
                        .param("doctorId", doctorId.toString())
                        .param("petCategoryId", petCategoryId.toString())
                        .param("petOwnerId", petOwnerId.toString())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.petName", is("Buddy")))
                .andExpect(jsonPath("$.id").exists());
    }



    @Test
    public void testGetPetById() throws Exception {
        mockMvc.perform(get("/pets/{pet-id}", existingPetId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(existingPetId))
                .andExpect(jsonPath("$.petName", is("OriginalName")));
    }

    @Test
    public void testFindAllPets() throws Exception {
        mockMvc.perform(get("/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testDeletePetById() throws Exception {
        mockMvc.perform(delete("/pets/{pet-id}", existingPetId))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdatePet() throws Exception {
        mockMvc.perform(multipart("/pets/{pet-id}", existingPetId)
                        // Instead of .file(), use param()
                        .param("petName", "UpdatedName")
                        // Override POST to PUT
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.petName", is("UpdatedName")));
    }


}
