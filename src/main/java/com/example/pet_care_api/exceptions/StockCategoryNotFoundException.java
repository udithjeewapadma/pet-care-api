package com.example.pet_care_api.exceptions;

public class StockCategoryNotFoundException extends RuntimeException {
    public StockCategoryNotFoundException(String message) {
        super(message);
    }
}
