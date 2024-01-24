package com.example.demo.services;

import com.example.demo.entities.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@ConditionalOnProperty(prefix = "app", name = "generate-students", havingValue = "true")
public class StudentGenerator {

    private final List<Student> students;

    public StudentGenerator() {
        students = new ArrayList<>();
    }

    public List<Student> generateStudents() {
        List<String> list = List.of("Иван", "Василий", "Ангелина", "Олег", "Вероника");
        Random rnd = new Random();
        list.forEach(name ->
            students.add(
                    new Student(name, getLastName(name), 18 + rnd.nextInt(10))
            )
        );
        return students;
    }

    private String getLastName(String name) {
        int len = name.length();
        char last = name.charAt(len - 1);
        return switch (last) {
            case 'й' ->
                    name.substring(0, len - 2) + "ов";
            case 'а' ->
                    name.substring(0, len - 1) + "ова";
            default -> name + "ов";
        };
    }
}
