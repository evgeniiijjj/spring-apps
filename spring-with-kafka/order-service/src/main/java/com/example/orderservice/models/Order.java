package com.example.orderservice.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Order {
    private String product;
    private Integer quantity;
}
