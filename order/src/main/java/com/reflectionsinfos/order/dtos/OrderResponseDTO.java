package com.reflectionsinfos.order.dtos;

import com.reflectionsinfos.order.entities.OrderStatus;

public class OrderResponseDTO {
    private Long orderId;
    private String customerName;
    private Double amount;
    private OrderStatus status;

    public OrderResponseDTO(Long orderId, String customerName, Double amount, OrderStatus status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.amount = amount;
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Long getOrderId() {
        return orderId;
    }
}
