package com.example.pet_care_api.controllers.dto.request;

import com.example.pet_care_api.models.OrderStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateOrderRequestDTO {

    @NotBlank(message = "Order name is required")
    private String orderName;

    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    @DecimalMin(value = "0.0", inclusive = false, message = "Total amount must be greater than 0")
    private double totalAmount;

    @NotNull(message = "Order status is required")
    private OrderStatus orderStatus;
}
