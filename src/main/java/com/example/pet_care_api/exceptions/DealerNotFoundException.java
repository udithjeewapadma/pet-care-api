package com.example.pet_care_api.exceptions;

public class DealerNotFoundException extends RuntimeException {
    public DealerNotFoundException(String message) {
        super(message);
    }
}
