package com.devops.order_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @GetMapping
    public List<Order> getOrders() {
        return List.of(
                new Order(101L, 1L, "Laptop", "CREATED"),
                new Order(102L, 2L, "Mobile", "SHIPPED"),
                new Order(103L, 3L, "Monitor", "DELIVERED")
        );
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return new Order(
                id,
                1L,
                "Product-" + id,
                "CREATED"
        );
    }

    record Order(
            Long id,
            Long userId,
            String product,
            String status
    ) {
    }
}