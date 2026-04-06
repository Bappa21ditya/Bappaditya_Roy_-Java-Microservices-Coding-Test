package com.reflectionsinfos.order.service.impl;

import com.reflectionsinfos.order.entities.Order;
import com.reflectionsinfos.order.entities.OrderStatus;
import com.reflectionsinfos.order.exception.OrderNotFoundException;
import com.reflectionsinfos.order.repositories.OrderRepository;
import com.reflectionsinfos.order.service.OrderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Qualifier("offlineOrderService")
public class OfflineOrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OfflineOrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.NEW);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Offline Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return  orderRepository.findAll();
    }

    @Override
    public Order updateStatus(Long orderId, OrderStatus status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
