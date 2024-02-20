package com.example.orderservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEvent {
    private String product;
    private String quantity;
}
