package com.example.demo.event;

import com.example.demo.enums.Messages;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteStudentEvent implements Event {
    private final int id;
    private final boolean success;

    @Override
    public String getEventMessage() {
        if (success) {
            return String.valueOf(id);
        }
        return Messages.ID_MISSING.getStringMessage(id);
    }
}
