package com.example.orderservice.listeners;

import com.example.orderservice.models.OrderStatusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaOrderStatusServiceListener {

    @KafkaListener(topics = "${app.kafka.kafkaOrderStatusServiceTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "orderStatusServiceConcurrentKafkaListenerContainerFactory")
    public void listen(@Payload OrderStatusService message,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                       @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(value = KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Received message: {}", message);
        log.info("Key: {}, Partition: {}; Topic: {}; Timestamp: {}", key, partition, topic, timestamp);
    }
}
