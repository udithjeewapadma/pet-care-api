package com.example.pet_care_api.exceptions;

public class PetNotFoundException extends RuntimeException {
    public PetNotFoundException(String message) {
        super(message);
    }
}
