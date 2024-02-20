package com.example.orderservice.controllers;

import com.example.orderservice.models.OrderEvent;
import com.example.orderservice.models.Order;
import com.example.orderservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService service;

    @PostMapping("/api/orders/order")
    public ResponseEntity<OrderEvent> createOrder(@RequestBody Order order) {

        return ResponseEntity.ok(service.createOrder(order));
    }
}
