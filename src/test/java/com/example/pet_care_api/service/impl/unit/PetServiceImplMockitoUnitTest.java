package com.example.pet_care_api.service.impl.unit;

import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.example.pet_care_api.controllers.dto.request.CreatePetRequestDTO;
import com.example.pet_care_api.controllers.dto.response.PetResponseDTO;
import com.example.pet_care_api.exceptions.PetCategoryNotFoundException;
import com.example.pet_care_api.exceptions.PetNotFoundException;
import com.example.pet_care_api.models.Doctor;
import com.example.pet_care_api.models.Pet;
import com.example.pet_care_api.models.PetCategory;
import com.example.pet_care_api.models.PetOwner;
import com.example.pet_care_api.repositories.DoctorRepository;
import com.example.pet_care_api.repositories.PetCategoryRepository;
import com.example.pet_care_api.repositories.PetOwnerRepository;
import com.example.pet_care_api.repositories.PetRepository;
import com.example.pet_care_api.service.impl.PetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetServiceImplMockitoUnitTest {

    @InjectMocks
    private PetServiceImpl petService;

    @Mock
    private PetRepository petRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PetOwnerRepository petOwnerRepository;

    @Mock
    private PetCategoryRepository petCategoryRepository;

    @Mock
    private Cloudinary cloudinary;

    @Mock
    private Uploader uploader;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(cloudinary.uploader()).thenReturn(uploader);
    }

    @Test
    void testCreatePet_success() throws Exception {
        Long doctorId = 1L;
        Long petCategoryId = 2L;
        Long petOwnerId = 3L;

        PetCategory petCategory = new PetCategory();
        petCategory.setId(petCategoryId);

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        PetOwner petOwner = new PetOwner();
        petOwner.setId(petOwnerId);

        when(petCategoryRepository.findById(petCategoryId)).thenReturn(Optional.of(petCategory));
        when(doctorRepository.findById(doctorId)).thenReturn(Optional.of(doctor));
        when(petOwnerRepository.findById(petOwnerId)).thenReturn(Optional.of(petOwner));

        CreatePetRequestDTO dto = new CreatePetRequestDTO();
        dto.setPetName("Fluffy");
        dto.setGender("Male");
        dto.setBirthDate(String.valueOf(LocalDate.of(2020, 1, 1)));

        MultipartFile fileMock = mock(MultipartFile.class);
        dto.setImageFiles(Collections.singletonList(fileMock));

        byte[] fileBytes = new byte[]{1, 2, 3};
        when(fileMock.getBytes()).thenReturn(fileBytes);

        Map<String, Object> uploadResult = new HashMap<>();
        uploadResult.put("url", "http://fakeurl.com/image.jpg");
        when(uploader.upload(eq(fileBytes), anyMap())).thenReturn(uploadResult);

        Pet savedPet = new Pet();
        savedPet.setId(10L);
        savedPet.setPetName(dto.getPetName());
        savedPet.setGender(dto.getGender());
        savedPet.setBirthDate(dto.getBirthDate());
        savedPet.setPetCategory(petCategory);
        savedPet.setDoctor(doctor);
        savedPet.setPetOwner(petOwner);
        savedPet.setImageUrl(List.of("http://fakeurl.com/image.jpg"));

        when(petRepository.save(any(Pet.class))).thenReturn(savedPet);

        PetResponseDTO responseDTO = petService.createPet(doctorId, petCategoryId, petOwnerId, dto);

        assertNotNull(responseDTO);
        assertEquals(savedPet.getId(), responseDTO.getId());
        assertEquals("Fluffy", responseDTO.getPetName());
        assertEquals("Male", responseDTO.getGender());
        assertEquals("2020-01-01", responseDTO.getBirthDate());
        assertEquals(List.of("http://fakeurl.com/image.jpg"), responseDTO.getImageUrls());
        assertEquals(petCategoryId, responseDTO.getPetCategoryId());
        assertEquals(petOwnerId, responseDTO.getPetOwnerId());
        assertEquals(doctorId, responseDTO.getDoctorId());
    }

    @Test
    void testCreatePet_petCategoryNotFound() {
        when(petCategoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        CreatePetRequestDTO dto = new CreatePetRequestDTO();

        PetCategoryNotFoundException exception = assertThrows(PetCategoryNotFoundException.class, () -> {
            petService.createPet(1L, 1L, 1L, dto);
        });

        assertEquals("Pet Category Not Found", exception.getMessage());
    }

    @Test
    void testGetPetById_success() throws PetNotFoundException {
        Long petId = 1L;
        Pet pet = new Pet();
        pet.setId(petId);
        pet.setPetName("Buddy");
        pet.setGender("Female");
        pet.setBirthDate(String.valueOf(LocalDate.of(2019, 5, 10)));
        pet.setImageUrl(List.of("url1", "url2"));

        PetOwner petOwner = new PetOwner();
        petOwner.setId(2L);

        Doctor doctor = new Doctor();
        doctor.setId(3L);

        PetCategory petCategory = new PetCategory();
        petCategory.setId(4L);

        pet.setPetOwner(petOwner);
        pet.setDoctor(doctor);
        pet.setPetCategory(petCategory);

        when(petRepository.findById(petId)).thenReturn(Optional.of(pet));

        PetResponseDTO dto = petService.getPetById(petId);

        assertNotNull(dto);
        assertEquals(petId, dto.getId());
        assertEquals("Buddy", dto.getPetName());
        assertEquals("Female", dto.getGender());
        assertEquals(List.of("url1", "url2"), dto.getImageUrls());
        assertEquals(2L, dto.getPetOwnerId());
        assertEquals(3L, dto.getDoctorId());
        assertEquals(4L, dto.getPetCategoryId());
    }

    @Test
    void testGetPetById_petNotFound() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        PetNotFoundException exception = assertThrows(PetNotFoundException.class, () -> {
            petService.getPetById(1L);
        });

        assertEquals("Pet Not Found", exception.getMessage());
    }

    @Test
    void testFindAllPets() {
        Pet pet1 = new Pet();
        pet1.setId(1L);
        pet1.setPetName("Pet1");
        pet1.setGender("M");
        pet1.setBirthDate(String.valueOf(LocalDate.of(2020, 1, 1)));
        pet1.setImageUrl(List.of("url1"));
        pet1.setPetOwner(new PetOwner(){{
            setId(10L);
        }});
        pet1.setDoctor(new Doctor(){{
            setId(20L);
        }});
        pet1.setPetCategory(new PetCategory(){{
            setId(30L);
        }});

        Pet pet2 = new Pet();
        pet2.setId(2L);
        pet2.setPetName("Pet2");
        pet2.setGender("F");
        pet2.setBirthDate(String.valueOf(LocalDate.of(2019, 2, 2)));
        pet2.setImageUrl(List.of("url2"));
        pet2.setPetOwner(new PetOwner(){{
            setId(11L);
        }});
        pet2.setDoctor(new Doctor(){{
            setId(21L);
        }});
        pet2.setPetCategory(new PetCategory(){{
            setId(31L);
        }});

        when(petRepository.findAll()).thenReturn(List.of(pet1, pet2));

        List<PetResponseDTO> pets = petService.findAllPets();

        assertEquals(2, pets.size());
        assertEquals("Pet1", pets.get(0).getPetName());
        assertEquals("Pet2", pets.get(1).getPetName());
    }

    @Test
    void testDeletePetById() {
        Long petId = 5L;

        doNothing().when(petRepository).deleteById(petId);

        petService.deletePetById(petId);

        verify(petRepository, times(1)).deleteById(petId);
    }

    @Test
    void testUpdatePet_success() throws IOException, PetNotFoundException {
        Long petId = 1L;

        Pet existingPet = new Pet();
        existingPet.setId(petId);
        existingPet.setPetOwner(new PetOwner(){{
            setId(10L);
        }});
        existingPet.setDoctor(new Doctor(){{
            setId(20L);
        }});
        existingPet.setPetCategory(new PetCategory(){{
            setId(30L);
        }});

        when(petRepository.findById(petId)).thenReturn(Optional.of(existingPet));

        CreatePetRequestDTO dto = new CreatePetRequestDTO();
        dto.setPetName("UpdatedName");
        dto.setGender("F");
        dto.setBirthDate(String.valueOf(LocalDate.of(2022, 3, 3)));

        MultipartFile fileMock = mock(MultipartFile.class);
        dto.setImageFiles(Collections.singletonList(fileMock));
        byte[] fileBytes = new byte[]{1, 2, 3};
        when(fileMock.getBytes()).thenReturn(fileBytes);

        Map<String, Object> uploadResult = new HashMap<>();
        uploadResult.put("url", "http://fakeurl.com/updatedimage.jpg");
        when(uploader.upload(eq(fileBytes), anyMap())).thenReturn(uploadResult);

        Pet updatedPet = new Pet();
        updatedPet.setId(petId);
        updatedPet.setPetName(dto.getPetName());
        updatedPet.setGender(dto.getGender());
        updatedPet.setBirthDate(dto.getBirthDate());
        updatedPet.setImageUrl(List.of("http://fakeurl.com/updatedimage.jpg"));
        updatedPet.setPetOwner(existingPet.getPetOwner());
        updatedPet.setDoctor(existingPet.getDoctor());
        updatedPet.setPetCategory(existingPet.getPetCategory());

        when(petRepository.save(any(Pet.class))).thenReturn(updatedPet);

        PetResponseDTO responseDTO = petService.updatePet(petId, dto);

        assertNotNull(responseDTO);
        assertEquals(petId, responseDTO.getId());
        assertEquals("UpdatedName", responseDTO.getPetName());
        assertEquals("F", responseDTO.getGender());
        assertEquals(List.of("http://fakeurl.com/updatedimage.jpg"), responseDTO.getImageUrls());
    }

    @Test
    void testUpdatePet_petNotFound() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        CreatePetRequestDTO dto = new CreatePetRequestDTO();

        PetNotFoundException exception = assertThrows(PetNotFoundException.class, () -> {
            petService.updatePet(1L, dto);
        });

        assertEquals("Pet not found", exception.getMessage());
    }
}
