package com.example.pet_care_api.exceptions;

public class PetOwnerNotFoundException extends RuntimeException {
    public PetOwnerNotFoundException(String message) {
        super(message);
    }
}
