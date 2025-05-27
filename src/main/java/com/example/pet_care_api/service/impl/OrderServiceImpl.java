package com.example.pet_care_api.service.impl;

import com.example.pet_care_api.controllers.dto.request.CreateOrderRequestDTO;
import com.example.pet_care_api.exceptions.DealerNotFoundException;
import com.example.pet_care_api.models.Dealer;
import com.example.pet_care_api.models.Order;
import com.example.pet_care_api.models.OrderStatus;
import com.example.pet_care_api.repositories.DealerRepository;
import com.example.pet_care_api.repositories.OrderRepository;
import com.example.pet_care_api.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DealerRepository dealerRepository;

    @Override
    public Order createOrder(Long dealerId, CreateOrderRequestDTO createOrderRequestDTO)
            throws DealerNotFoundException {

        Dealer dealer = dealerRepository.findById(dealerId)
                .orElseThrow(() -> new DealerNotFoundException("Dealer not found"));

        Order order = new Order();
        order.setOrderName(createOrderRequestDTO.getOrderName());
        order.setQuantity(createOrderRequestDTO.getQuantity());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setTotalAmount(createOrderRequestDTO.getTotalAmount());
        order.setDealer(dealer);
        return orderRepository.save(order);
    }
}
