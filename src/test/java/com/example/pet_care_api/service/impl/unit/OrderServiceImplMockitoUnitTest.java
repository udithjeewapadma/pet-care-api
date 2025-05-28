package com.example.pet_care_api.service.impl.unit;

import com.example.pet_care_api.controllers.dto.request.CreateOrderRequestDTO;
import com.example.pet_care_api.controllers.dto.response.OrderResponseDTO;
import com.example.pet_care_api.exceptions.DealerNotFoundException;
import com.example.pet_care_api.exceptions.OrderNotFoundException;
import com.example.pet_care_api.models.Dealer;
import com.example.pet_care_api.models.Order;
import com.example.pet_care_api.models.OrderStatus;
import com.example.pet_care_api.repositories.DealerRepository;
import com.example.pet_care_api.repositories.OrderRepository;
import com.example.pet_care_api.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceImplMockitoUnitTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DealerRepository dealerRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrder_Success() throws DealerNotFoundException {
        Long dealerId = 1L;
        Dealer dealer = new Dealer();
        dealer.setId(dealerId);

        CreateOrderRequestDTO dto = new CreateOrderRequestDTO();
        dto.setOrderName("Test Order");
        dto.setQuantity(5);
        dto.setTotalAmount(100.0);

        when(dealerRepository.findById(dealerId)).thenReturn(Optional.of(dealer));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order createdOrder = orderService.createOrder(dealerId, dto);

        assertNotNull(createdOrder);
        assertEquals("Test Order", createdOrder.getOrderName());
        assertEquals(5, createdOrder.getQuantity());
        assertEquals(100.0, createdOrder.getTotalAmount());
        assertEquals(OrderStatus.PENDING, createdOrder.getOrderStatus());
        assertEquals(dealer, createdOrder.getDealer());
    }

    @Test
    void testCreateOrder_DealerNotFound() {
        Long dealerId = 1L;
        CreateOrderRequestDTO dto = new CreateOrderRequestDTO();

        when(dealerRepository.findById(dealerId)).thenReturn(Optional.empty());

        assertThrows(DealerNotFoundException.class, () -> orderService.createOrder(dealerId, dto));
    }

    @Test
    void testFindOrderById_Success() throws OrderNotFoundException {
        Long orderId = 1L;
        Dealer dealer = new Dealer();
        dealer.setId(2L);

        Order order = new Order();
        order.setId(orderId);
        order.setOrderName("Sample Order");
        order.setQuantity(10);
        order.setTotalAmount(200.0);
        order.setOrderStatus(OrderStatus.PENDING);
        order.setDealer(dealer);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        OrderResponseDTO responseDTO = orderService.findOrderById(orderId);

        assertNotNull(responseDTO);
        assertEquals(orderId, responseDTO.getId());
        assertEquals("Sample Order", responseDTO.getOrderName());
        assertEquals(10, responseDTO.getQuantity());
        assertEquals(200.0, responseDTO.getTotalAmount());
        assertEquals(OrderStatus.PENDING, responseDTO.getOrderStatus());
        assertEquals(dealer.getId(), responseDTO.getDealerId());
    }

    @Test
    void testFindOrderById_OrderNotFound() {
        Long orderId = 1L;
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.findOrderById(orderId));
    }

    @Test
    void testFindAllOrders() {
        Dealer dealer = new Dealer();
        dealer.setId(3L);

        Order order1 = new Order();
        order1.setId(1L);
        order1.setOrderName("Order 1");
        order1.setQuantity(2);
        order1.setTotalAmount(50.0);
        order1.setOrderStatus(OrderStatus.PENDING);
        order1.setDealer(dealer);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setOrderName("Order 2");
        order2.setQuantity(3);
        order2.setTotalAmount(75.0);
        order2.setOrderStatus(OrderStatus.PENDING);
        order2.setDealer(dealer);

        when(orderRepository.findAll()).thenReturn(List.of(order1, order2));

        List<OrderResponseDTO> allOrders = orderService.findAllOrders();

        assertEquals(2, allOrders.size());
        assertEquals("Order 1", allOrders.get(0).getOrderName());
        assertEquals("Order 2", allOrders.get(1).getOrderName());
    }

    @Test
    void testDeleteOrderById() {
        Long orderId = 1L;

        doNothing().when(orderRepository).deleteById(orderId);

        orderService.deleteOrderById(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    void testUpdateOrderById_Success() throws OrderNotFoundException {
        Long orderId = 1L;

        Order existingOrder = new Order();
        existingOrder.setId(orderId);
        existingOrder.setOrderName("Old Order");
        existingOrder.setQuantity(1);
        existingOrder.setOrderStatus(OrderStatus.PENDING);
        existingOrder.setTotalAmount(10.0);

        CreateOrderRequestDTO dto = new CreateOrderRequestDTO();
        dto.setOrderName("Updated Order");
        dto.setQuantity(5);
        dto.setOrderStatus(OrderStatus.COMPLETED);
        dto.setTotalAmount(150.0);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order updatedOrder = orderService.updateOrderById(orderId, dto);

        assertNotNull(updatedOrder);
        assertEquals("Updated Order", updatedOrder.getOrderName());
        assertEquals(5, updatedOrder.getQuantity());
        assertEquals(OrderStatus.COMPLETED, updatedOrder.getOrderStatus());
        assertEquals(150.0, updatedOrder.getTotalAmount());
    }

    @Test
    void testUpdateOrderById_OrderNotFound() {
        Long orderId = 1L;
        CreateOrderRequestDTO dto = new CreateOrderRequestDTO();

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> orderService.updateOrderById(orderId, dto));
    }
}
