package com.example.demo.services;

import com.example.demo.entities.Student;
import com.example.demo.enums.Messages;
import com.example.demo.event.AddStudentEvent;
import com.example.demo.event.DeleteStudentEvent;
import com.example.demo.event.EventHolder;
import com.example.demo.repo.StudentRepo;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@AllArgsConstructor
@ShellComponent
public class StudentService {

    private StudentRepo repo;
    private final ApplicationEventPublisher publisher;

    @ShellMethod(key = "print")
    public String printStudents() {
        return Messages.TABLE.getStringMessage(repo.getArray());
    }

    @ShellMethod(key = "add")
    public void addNewStudent(@ShellOption({"firstname", "fn"}) String firstName,
                                @ShellOption({"lastname", "ln"}) String lastName,
                                @ShellOption({"age", "a"}) int age) {
        Student student = new Student(firstName, lastName, age);
        repo.addStudent(student);
        publisher.publishEvent(new EventHolder(this, new AddStudentEvent(student)));
    }

    @ShellMethod(key = "delete")
    public void deleteStudent(@ShellOption({"id", "i"}) int id) {
        boolean success = repo.getStream()
                .filter(st -> st.getId() == id)
                .findFirst()
                .map(repo::deleteStudent)
                .orElse(false);
        publisher.publishEvent(new EventHolder(this, new DeleteStudentEvent(id, success)));
    }

    @ShellMethod(key = "clean")
    public String deleteAllStudents() {
        repo.deleteAllStudents();
        return printStudents();
    }
}
