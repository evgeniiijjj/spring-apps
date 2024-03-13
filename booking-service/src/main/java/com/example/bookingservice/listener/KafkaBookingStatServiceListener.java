package com.example.bookingservice.listener;

import com.example.bookingservice.entity.stat.BookingStat;
import com.example.bookingservice.entity.stat.UserStat;
import com.example.bookingservice.event.Event;
import com.example.bookingservice.service.stat.StatService;
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
public class KafkaBookingStatServiceListener {

    private final StatService service;

    @KafkaListener(topics = "${app.kafka.registrationTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "bookingStatServiceConcurrentKafkaListenerContainerFactory")
    public void listenRegistration(@Payload Event<UserStat> message,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                       @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(value = KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Received message: {}", message);
        log.info("Key: {}, Partition: {}; Topic: {}; Timestamp: {}", key, partition, topic, timestamp);
        service.saveRegistration(message.getStatEntity());
    }

    @KafkaListener(topics = "${app.kafka.bookingTopic}",
            groupId = "${app.kafka.kafkaMessageGroupId}",
            containerFactory = "bookingStatServiceConcurrentKafkaListenerContainerFactory")
    public void listenBooking(@Payload Event<BookingStat> message,
                       @Header(value = KafkaHeaders.RECEIVED_KEY, required = false) String key,
                       @Header(value = KafkaHeaders.RECEIVED_TOPIC) String topic,
                       @Header(value = KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                       @Header(value = KafkaHeaders.RECEIVED_TIMESTAMP) Long timestamp) {
        log.info("Received message: {}", message);
        log.info("Key: {}, Partition: {}; Topic: {}; Timestamp: {}", key, partition, topic, timestamp);
        service.saveBooking(message.getStatEntity());
    }
}
