package com.example.pet_care_api.controllers.dto.response;

import com.example.pet_care_api.models.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderResponseDTO {

    private Long id;
    private String orderName;
    private int quantity;
    private double totalAmount;
    private OrderStatus orderStatus;
    private Long dealerId;
}
