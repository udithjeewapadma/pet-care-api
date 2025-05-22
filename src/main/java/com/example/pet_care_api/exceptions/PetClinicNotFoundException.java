package com.example.pet_care_api.exceptions;

public class PetClinicNotFoundException extends RuntimeException {
    public PetClinicNotFoundException(String message) {
        super(message);
    }
}
