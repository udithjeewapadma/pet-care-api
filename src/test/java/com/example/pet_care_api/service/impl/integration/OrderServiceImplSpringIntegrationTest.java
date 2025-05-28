package com.example.pet_care_api.service.impl.integration;

import com.example.pet_care_api.controllers.dto.request.CreateOrderRequestDTO;
import com.example.pet_care_api.exceptions.DealerNotFoundException;
import com.example.pet_care_api.exceptions.OrderNotFoundException;
import com.example.pet_care_api.models.Dealer;
import com.example.pet_care_api.models.Order;
import com.example.pet_care_api.models.OrderStatus;
import com.example.pet_care_api.repositories.DealerRepository;
import com.example.pet_care_api.repositories.OrderRepository;
import com.example.pet_care_api.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceImplSpringIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private DealerRepository dealerRepository;

    @Autowired
    private OrderRepository orderRepository;

    private Dealer savedDealer;

    @BeforeEach
    void setup() {
        // Setup a dealer in DB for tests
        Dealer dealer = new Dealer();
        dealer.setDealerName("Integration Dealer");
        dealer.setPhoneNumber("0712345678");
        dealer.setEmail("dealer@example.com");
        savedDealer = dealerRepository.save(dealer);
    }

    @Test
    void testCreateOrder() throws DealerNotFoundException {
        CreateOrderRequestDTO dto = new CreateOrderRequestDTO();
        dto.setOrderName("Integration Order");
        dto.setQuantity(10);
        dto.setTotalAmount(500.0);

        Order order = orderService.createOrder(savedDealer.getId(), dto);

        assertNotNull(order.getId());
        assertEquals("Integration Order", order.getOrderName());
        assertEquals(10, order.getQuantity());
        assertEquals(500.0, order.getTotalAmount());
        assertEquals(savedDealer.getId(), order.getDealer().getId());
        assertEquals(OrderStatus.PENDING, order.getOrderStatus());
    }

    @Test
    void testFindOrderById() throws DealerNotFoundException, OrderNotFoundException {
        CreateOrderRequestDTO dto = new CreateOrderRequestDTO();
        dto.setOrderName("Order To Find");
        dto.setQuantity(1);
        dto.setTotalAmount(100.0);

        Order order = orderService.createOrder(savedDealer.getId(), dto);

        var foundOrderDTO = orderService.findOrderById(order.getId());

        assertEquals(order.getId(), foundOrderDTO.getId());
        assertEquals(order.getOrderName(), foundOrderDTO.getOrderName());
    }

    @Test
    void testDeleteOrderById() throws DealerNotFoundException {
        CreateOrderRequestDTO dto = new CreateOrderRequestDTO();
        dto.setOrderName("Order To Delete");
        dto.setQuantity(3);
        dto.setTotalAmount(300.0);

        Order order = orderService.createOrder(savedDealer.getId(), dto);

        orderService.deleteOrderById(order.getId());

        assertFalse(orderRepository.findById(order.getId()).isPresent());
    }

    @Test
    void testUpdateOrderById() throws DealerNotFoundException, OrderNotFoundException {
        CreateOrderRequestDTO createDto = new CreateOrderRequestDTO();
        createDto.setOrderName("Original Order");
        createDto.setQuantity(1);
        createDto.setTotalAmount(100.0);

        Order order = orderService.createOrder(savedDealer.getId(), createDto);

        CreateOrderRequestDTO updateDto = new CreateOrderRequestDTO();
        updateDto.setOrderName("Updated Order");
        updateDto.setQuantity(5);
        updateDto.setOrderStatus(OrderStatus.COMPLETED);
        updateDto.setTotalAmount(500.0);

        Order updatedOrder = orderService.updateOrderById(order.getId(), updateDto);

        assertEquals("Updated Order", updatedOrder.getOrderName());
        assertEquals(5, updatedOrder.getQuantity());
        assertEquals(OrderStatus.COMPLETED, updatedOrder.getOrderStatus());
        assertEquals(500.0, updatedOrder.getTotalAmount());
    }
}
