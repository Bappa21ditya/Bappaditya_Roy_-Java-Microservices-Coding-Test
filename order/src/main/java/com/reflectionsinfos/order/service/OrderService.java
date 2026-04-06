package com.reflectionsinfos.order.service;

import com.reflectionsinfos.order.entities.Order;
import com.reflectionsinfos.order.entities.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(Order order);

    Order getOrderById(Long orderId);

    List<Order> getAllOrders();

    Order updateStatus(Long orderId, OrderStatus status);
}
