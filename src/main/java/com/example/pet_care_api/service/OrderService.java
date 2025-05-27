package com.example.pet_care_api.service;

import com.example.pet_care_api.controllers.dto.request.CreateOrderRequestDTO;
import com.example.pet_care_api.controllers.dto.response.OrderResponseDTO;
import com.example.pet_care_api.exceptions.DealerNotFoundException;
import com.example.pet_care_api.exceptions.OrderNotFoundException;
import com.example.pet_care_api.models.Order;

import java.util.List;

public interface OrderService {

    Order createOrder(Long dealerId,CreateOrderRequestDTO createOrderRequestDTO) throws DealerNotFoundException;

    OrderResponseDTO findOrderById(Long orderId) throws OrderNotFoundException;

    List<OrderResponseDTO> findAllOrders();

    void deleteOrderById(Long orderId);
}
