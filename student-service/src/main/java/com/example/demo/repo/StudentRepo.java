package com.example.demo.repo;

import com.example.demo.entities.Student;
import com.example.demo.services.StudentGenerator;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
public class StudentRepo {

    private List<Student> students;

    public StudentRepo(@Nullable StudentGenerator generator) {
        students = new ArrayList<>();
        if (generator != null) {
            students.addAll(generator.generateStudents());
        }
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public boolean deleteStudent(Student student) {
        return students.remove(student);
    }

    public Object[] getArray() {
        return students.toArray();
    }

    public Stream<Student> getStream() {
        return students.stream();
    }

    public void deleteAllStudents() {
        students = new ArrayList<>();
    }

}