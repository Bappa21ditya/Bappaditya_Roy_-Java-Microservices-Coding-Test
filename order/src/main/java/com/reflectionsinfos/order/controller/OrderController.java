package com.reflectionsinfos.order.controller;

import com.reflectionsinfos.order.dtos.OrderRequestDTO;
import com.reflectionsinfos.order.dtos.OrderResponseDTO;
import com.reflectionsinfos.order.dtos.OrderStatusUpdateDTO;
import com.reflectionsinfos.order.entities.Order;
import com.reflectionsinfos.order.mapper.OrderMapper;
import com.reflectionsinfos.order.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper mapper;

public OrderController(
        @Qualifier("onlineOrderService") OrderService orderService,
        OrderMapper mapper) {
    this.orderService = orderService;
    this.mapper = mapper;
}

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderRequestDTO dto) {
        Order order = mapper.toEntity(dto);
        return ResponseEntity.ok(mapper.toDTO(orderService.createOrder(order)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toDTO(orderService.getOrderById(id)));
    }

    @GetMapping("/")
    public ResponseEntity<List<OrderResponseDTO>> getAll() {
        return ResponseEntity.ok(
                orderService.getAllOrders()
                        .stream()
                        .map(mapper::toDTO)
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(
            @PathVariable("id") Long orderId,
            @Valid @RequestBody OrderStatusUpdateDTO dto) {

        return ResponseEntity.ok(
                mapper.toDTO(orderService.updateStatus(orderId, dto.getStatus()))
        );
    }
}
