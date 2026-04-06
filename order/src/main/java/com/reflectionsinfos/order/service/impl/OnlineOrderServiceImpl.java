package com.reflectionsinfos.order.service.impl;

import com.reflectionsinfos.order.entities.Order;
import com.reflectionsinfos.order.entities.OrderStatus;
import com.reflectionsinfos.order.exception.InvalidStatusTransitionException;
import com.reflectionsinfos.order.exception.OrderNotFoundException;
import com.reflectionsinfos.order.repositories.OrderRepository;
import com.reflectionsinfos.order.service.OrderService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Qualifier("onlineOrderService")
public class OnlineOrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OnlineOrderServiceImpl(OrderRepository orderRepository) {
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
                .orElseThrow(() -> new OrderNotFoundException("Online Order not found"));
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

@Override
public Order updateStatus(Long orderId, OrderStatus status) {
    Order order = getOrderById(orderId);

    if (!isValidTransition(order.getStatus(), status)) {
        throw new InvalidStatusTransitionException("Invalid status transition");
    }

    order.setStatus(status);
    return orderRepository.save(order);
}

    private boolean isValidTransition(OrderStatus current, OrderStatus next) {
        return (current == OrderStatus.NEW && next == OrderStatus.PROCESSING)
                || (current == OrderStatus.PROCESSING && next == OrderStatus.COMPLETED);
    }
}
