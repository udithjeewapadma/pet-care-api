package com.example.pet_care_api.exceptions;

public class PetCategoryNotFoundException extends RuntimeException {
  public PetCategoryNotFoundException(String message) {
    super(message);
  }
}
