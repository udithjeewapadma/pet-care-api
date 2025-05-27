package com.example.pet_care_api.controllers.dto.request;

import com.example.pet_care_api.models.OrderStatus;
import lombok.Data;

@Data
public class CreateOrderRequestDTO {

    private String orderName;
    private int quantity;
    private double totalAmount;
    private OrderStatus orderStatus;
}
