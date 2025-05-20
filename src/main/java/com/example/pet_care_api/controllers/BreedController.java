package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreateBreedRequestDTO;
import com.example.pet_care_api.controllers.dto.response.BreedResponseDTO;
import com.example.pet_care_api.models.Breed;
import com.example.pet_care_api.service.BreedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/breeds")
public class BreedController {

    @Autowired
    private BreedService breedService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BreedResponseDTO createBreed(@RequestBody CreateBreedRequestDTO createBreedRequestDTO) {

        Breed breed = breedService.createBreed(createBreedRequestDTO);

        BreedResponseDTO breedResponseDTO = new BreedResponseDTO();
        breedResponseDTO.setId(breed.getId());
        breedResponseDTO.setBreedName(breed.getBreedName());

        return breedResponseDTO;
    }

    @GetMapping("/{breed-id}")
    public BreedResponseDTO getBreedById(@PathVariable("breed-id") Long breedId) {
        return breedService.getBreedById(breedId);
    }

    @GetMapping
    public List<BreedResponseDTO> getAllBreeds() {
        return breedService.getAllBreeds();
    }

    @DeleteMapping("/{breed-id}")
    private void deleteBreedById(@PathVariable("breed-id") Long breedId) {
        breedService.deleteBreedById(breedId);
    }

    @PutMapping("/{breed-id}")
    public BreedResponseDTO updateBreed(@PathVariable("breed-id") Long id,
                                        @RequestBody CreateBreedRequestDTO createBreedRequestDTO) {

        Breed breed = breedService.updateBreeById(id, createBreedRequestDTO);
        BreedResponseDTO breedResponseDTO = new BreedResponseDTO();
        breedResponseDTO.setBreedName(breed.getBreedName());
        return breedResponseDTO;
    }

}
