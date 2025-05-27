package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateOrderRequestDTO;
import com.example.pet_care_api.exceptions.DealerNotFoundException;
import com.example.pet_care_api.models.Order;

public interface OrderService {

    Order createOrder(Long dealerId,CreateOrderRequestDTO createOrderRequestDTO) throws DealerNotFoundException;
}
