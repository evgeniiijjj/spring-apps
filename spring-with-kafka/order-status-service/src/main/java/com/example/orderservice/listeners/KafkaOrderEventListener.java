package com.example.orderservice.listeners;

import com.example.orderservice.enums.Status;
import com.example.orderservice.models.OrderStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaOrderEventListener {

    @Value("${app.kafka.kafkaOrderStatusServiceTopic}")
    private String kafkaOrderStatusTopic;
    private final KafkaTemplate<String, OrderStatusService> template;

    @KafkaListener(topics = "${app.kafka.kafkaOrderTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "orderEventConcurrentKafkaListenerContainerFactory")
    public void listen() {
        OrderStatusService orderStatusService = new OrderStatusService();
        orderStatusService.setStatus(Status.values()[new Random().nextInt(3)].toString());
        orderStatusService.setDate(Instant.now().truncatedTo(ChronoUnit.SECONDS));
        template.send(kafkaOrderStatusTopic, orderStatusService);
    }
}