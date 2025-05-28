package com.example.pet_care_api.service.impl.integration;

import com.example.pet_care_api.config.CloudinaryConfig;
import com.example.pet_care_api.controllers.dto.request.CreatePetRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetResponseDTO;
import com.example.pet_care_api.models.Doctor;
import com.example.pet_care_api.models.PetCategory;
import com.example.pet_care_api.models.PetOwner;
import com.example.pet_care_api.repositories.DoctorRepository;
import com.example.pet_care_api.repositories.PetCategoryRepository;
import com.example.pet_care_api.repositories.PetOwnerRepository;
import com.example.pet_care_api.repositories.PetRepository;
import com.example.pet_care_api.service.PetService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(CloudinaryConfig.class)
@Transactional
public class PetServiceImplSpringIntegrationTest {

    @Autowired private PetService petService;
    @Autowired private PetRepository petRepository;
    @Autowired private PetCategoryRepository petCategoryRepository;
    @Autowired private PetOwnerRepository petOwnerRepository;
    @Autowired private DoctorRepository doctorRepository;

    private static Long petId;
    private static Long doctorId;
    private static Long petOwnerId;
    private static Long petCategoryId;

    @BeforeEach
    void setUp() {
        PetCategory petCategory = new PetCategory();
        petCategory.setCategoryName("Dog");
        petCategory = petCategoryRepository.save(petCategory);

        Doctor doctor = new Doctor();
        doctor.setDoctorName("Dr. Smith");
        doctor = doctorRepository.save(doctor);

        PetOwner owner = new PetOwner();
        owner.setOwnerName("Alice");
        owner = petOwnerRepository.save(owner);

        petCategoryId = petCategory.getId();
        doctorId = doctor.getId();
        petOwnerId = owner.getId();
    }

    @Test
    @Order(1)
    void testCreatePet() throws Exception {
        MockMultipartFile imageFile = loadTestImage();

        CreatePetRequestDTO dto = new CreatePetRequestDTO();
        dto.setPetName("Buddy");
        dto.setGender("Male");
        dto.setBirthDate(String.valueOf(LocalDate.of(2020, 5, 10)));
        dto.setImageFiles(Collections.singletonList(imageFile));

        PetResponseDTO response = petService.createPet(doctorId, petCategoryId, petOwnerId, dto);

        assertNotNull(response.getId());
        assertEquals("Buddy", response.getPetName());

        petId = response.getId();
    }

    @Test
    @Order(2)
    void testGetPetById() throws Exception {
        testCreatePet(); // Ensure data exists

        PetResponseDTO pet = petService.getPetById(petId);
        assertNotNull(pet);
        assertEquals("Buddy", pet.getPetName());
    }

    @Test
    @Order(3)
    void testFindAllPets() throws Exception {
        testCreatePet();

        List<PetResponseDTO> allPets = petService.findAllPets();
        assertFalse(allPets.isEmpty());
        assertTrue(allPets.stream().anyMatch(p -> p.getPetName().equals("Buddy")));
    }

    @Test
    @Order(4)
    void testDeletePetById() throws Exception {
        testCreatePet();

        petService.deletePetById(petId);
        assertThrows(Exception.class, () -> petService.getPetById(petId));
    }

    // Helper method to load a valid image from test resources
    private MockMultipartFile loadTestImage() throws IOException {
        var inputStream = getClass().getClassLoader().getResourceAsStream("test-image.jpg");
        assertNotNull(inputStream, "Test image not found in resources");

        byte[] imageBytes = inputStream.readAllBytes();
        return new MockMultipartFile("image", "test-image.jpg", "image/jpeg", imageBytes);
    }
}
