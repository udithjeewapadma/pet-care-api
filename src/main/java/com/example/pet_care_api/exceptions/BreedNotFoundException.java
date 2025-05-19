package com.example.pet_care_api.exceptions;

public class BreedNotFoundException extends RuntimeException {
    public BreedNotFoundException(String message) {
        super(message);
    }
}
