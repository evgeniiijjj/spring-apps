package com.example.bookingservice.controller;

import com.example.bookingservice.service.stat.StatService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class StatController {

    private final StatService service;

    @GetMapping(value = "/api/stat")
    public ResponseEntity<?> getStat(HttpServletResponse response) {
        try {
            response.addHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=stat.zip");
            service.sendStat(response.getOutputStream());
            response.flushBuffer();
            return ResponseEntity.ok()
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
