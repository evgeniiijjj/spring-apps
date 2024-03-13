package com.example.bookingservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class AllElementsResult<T> {
    private long numberOfElements;
    private List<T> elements;
}
