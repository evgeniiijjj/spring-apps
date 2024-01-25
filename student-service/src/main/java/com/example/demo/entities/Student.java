package com.example.demo.entities;

import lombok.Getter;

@Getter
public class Student {
    private static int counter;

    private final int id;
    private final String firstName;
    private final String lastName;
    private final int age;

    public Student(String firstName, String lastName, int age) {
        id = ++counter;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }
}
