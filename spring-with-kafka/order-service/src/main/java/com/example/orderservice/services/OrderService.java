package com.example.orderservice.services;

import com.example.orderservice.models.OrderEvent;
import com.example.orderservice.models.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderService {

    @Value("${app.kafka.kafkaOrderTopic}")
    private String topicName;
    private final KafkaTemplate<String, OrderEvent> template;

    public OrderEvent createOrder(Order order) {
        OrderEvent event = new OrderEvent();
        event.setProduct(order.getProduct());
        event.setQuantity(order.getQuantity());
        template.send(topicName, event);
        return event;
    }
}
