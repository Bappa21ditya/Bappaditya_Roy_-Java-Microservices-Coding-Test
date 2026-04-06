package com.reflectionsinfos.order.repositories;

import com.reflectionsinfos.order.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Order save(Order order);

    Optional<Order> findById(Long orderId);

    List<Order> findAll();


}
