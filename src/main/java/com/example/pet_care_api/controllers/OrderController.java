package com.example.pet_care_api.controllers;

import com.example.pet_care_api.controllers.dto.request.CreateOrderRequestDTO;
import com.example.pet_care_api.controllers.dto.response.OrderResponseDTO;
import com.example.pet_care_api.exceptions.OrderNotFoundException;
import com.example.pet_care_api.models.Order;
import com.example.pet_care_api.models.OrderStatus;
import com.example.pet_care_api.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO createOrder(@RequestParam Long dealerId,
                                        @RequestBody CreateOrderRequestDTO createOrderRequestDTO) {
       Order order = orderService.createOrder(dealerId, createOrderRequestDTO);
       OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
       orderResponseDTO.setId(order.getId());
       orderResponseDTO.setOrderName(order.getOrderName());
       orderResponseDTO.setOrderStatus(OrderStatus.PENDING);
       orderResponseDTO.setQuantity(order.getQuantity());
       orderResponseDTO.setTotalAmount(order.getTotalAmount());
       orderResponseDTO.setDealerId(order.getDealer().getId());
       return orderResponseDTO;
    }

    @GetMapping("/{order-id}")
    public OrderResponseDTO findOrderById(@PathVariable("order-id") Long orderId)
            throws OrderNotFoundException {
        return orderService.findOrderById(orderId);
    }

    @GetMapping
    public List<OrderResponseDTO> findAllOrders() {
        return orderService.findAllOrders();
    }

    @DeleteMapping("/{order-id}")
    private void deleteOrderById(@PathVariable("order-id") Long orderId) {
        orderService.deleteOrderById(orderId);
    }

    @PutMapping("/{order-id}")
    private OrderResponseDTO updateOrder(@PathVariable("order-id") Long id,
                                         @RequestBody CreateOrderRequestDTO createOrderRequestDTO){
        Order order = orderService.updateOrderById(id, createOrderRequestDTO);
        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setId(order.getId());
        orderResponseDTO.setOrderName(order.getOrderName());
        orderResponseDTO.setOrderStatus(order.getOrderStatus());
        orderResponseDTO.setQuantity(order.getQuantity());
        orderResponseDTO.setTotalAmount(order.getTotalAmount());
        orderResponseDTO.setDealerId(order.getDealer().getId());
        return orderResponseDTO;
    }
}
