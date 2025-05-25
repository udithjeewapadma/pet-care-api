package com.example.pet_care_api.exceptions;

public class StockNotFoundException extends RuntimeException {
  public StockNotFoundException(String message) {
    super(message);
  }
}
