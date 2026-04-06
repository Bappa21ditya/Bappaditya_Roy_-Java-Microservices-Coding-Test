package com.reflectionsinfos.order.mapper;

import com.reflectionsinfos.order.dtos.OrderRequestDTO;
import com.reflectionsinfos.order.dtos.OrderResponseDTO;
import com.reflectionsinfos.order.entities.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public Order toEntity(OrderRequestDTO dto) {
        Order order = new Order();
        order.setCustomerName(dto.getCustomerName());
        order.setAmount(dto.getAmount());
        return order;
    }

    public OrderResponseDTO toDTO(Order order) {
        return new OrderResponseDTO(
                order.getOrderId(),
                order.getCustomerName(),
                order.getAmount(),
                order.getStatus()
        );
    }
}
