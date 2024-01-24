package com.example.demo.event;

import com.example.demo.entities.Student;
import com.example.demo.enums.Messages;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddStudentEvent implements Event {
    private final Student student;
    @Override
    public String getEventMessage() {
        return Messages.TABLE.getStringMessage(student);
    }
}
