package com.example.demo.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EventHolderListener {
    @EventListener
    public void listener(EventHolder eventHolder) {
        System.out.println(eventHolder.getEvent().getEventMessage());
    }
}
