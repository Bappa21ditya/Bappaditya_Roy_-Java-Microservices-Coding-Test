package com.reflectionsinfos.order.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import com.reflectionsinfos.order.entities.Order;
import com.reflectionsinfos.order.entities.OrderStatus;
import com.reflectionsinfos.order.exception.InvalidStatusTransitionException;
import com.reflectionsinfos.order.exception.OrderNotFoundException;
import com.reflectionsinfos.order.repositories.OrderRepository;
import com.reflectionsinfos.order.service.impl.OnlineOrderServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;



public class OnlineOrderServiceImplTest {
    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OnlineOrderServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //  Create Order
    @Test
    void testCreateOrder() {
        Order order = new Order();
        order.setCustomerName("John");
        order.setAmount(100.0);

        when(orderRepository.save(any(Order.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Order result = service.createOrder(order);

        assertNotNull(result);
        assertEquals(OrderStatus.NEW, result.getStatus());
        verify(orderRepository).save(order);
    }

    //  Get Order By Id - Success
    @Test
    void testGetOrderById_Success() {
        Order order = new Order();
        order.setOrderId(1L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = service.getOrderById(1L);

        assertEquals(1L, result.getOrderId());
    }

    //  Get Order By Id - Not Found
    @Test
    void testGetOrderById_NotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () ->
                service.getOrderById(1L)
        );
    }

    //  Get All Orders
    @Test
    void testGetAllOrders() {
        List<Order> orders = Arrays.asList(new Order(), new Order());

        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = service.getAllOrders();

        assertEquals(2, result.size());
    }

    //  Valid Transition: NEW → PROCESSING
    @Test
    void testUpdateStatus_ValidTransition() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setStatus(OrderStatus.NEW);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order updated = service.updateStatus(1L, OrderStatus.PROCESSING);

        assertEquals(OrderStatus.PROCESSING, updated.getStatus());
        verify(orderRepository).save(order);
    }

    //  Invalid Transition: NEW → COMPLETED
    @Test
    void testUpdateStatus_InvalidTransition() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setStatus(OrderStatus.NEW);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        assertThrows(InvalidStatusTransitionException.class, () ->
                service.updateStatus(1L, OrderStatus.COMPLETED)
        );

        verify(orderRepository, never()).save(any());
    }

    //  Valid Transition: PROCESSING → COMPLETED
    @Test
    void testUpdateStatus_ProcessingToCompleted() {
        Order order = new Order();
        order.setOrderId(1L);
        order.setStatus(OrderStatus.PROCESSING);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order updated = service.updateStatus(1L, OrderStatus.COMPLETED);

        assertEquals(OrderStatus.COMPLETED, updated.getStatus());
    }
}
