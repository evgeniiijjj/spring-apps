package com.example.orderservice.models;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class OrderStatusService {
    String status;
    Instant date;
}
