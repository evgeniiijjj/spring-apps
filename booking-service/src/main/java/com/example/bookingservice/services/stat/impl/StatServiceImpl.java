package com.example.bookingservice.services.stat.impl;

import com.example.bookingservice.entities.stat.BookingStat;
import com.example.bookingservice.entities.stat.UserStat;
import com.example.bookingservice.repositories.stat.BookingStatRepository;
import com.example.bookingservice.repositories.stat.UserStatRepository;
import com.example.bookingservice.services.stat.StatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor
@Service
public class StatServiceImpl implements StatService {
    private final UserStatRepository userStatRepository;
    private final BookingStatRepository bookingStatRepository;

    @Override
    public void saveRegistration(UserStat userStat) {
        userStatRepository.save(userStat);
    }

    @Override
    public void saveBooking(BookingStat bookingStat) {
        bookingStatRepository.save(bookingStat);
    }

    @Override
    public void sendStat(OutputStream outputStream) {

        try (ZipOutputStream zout = new ZipOutputStream(outputStream)) {
            ZipEntry entry = new ZipEntry("registration_stat.csv");
            zout.putNextEntry(entry);
            StringBuilder csvBuilder = new StringBuilder("user_id,user_name,user_registration_time");
            zout.write(
                    userStatRepository.findAll().stream()
                            .map(UserStat::toString)
                            .reduce(
                                    csvBuilder,
                                    StringBuilder::append,
                                    StringBuilder::append
                            )
                            .toString()
                            .getBytes(StandardCharsets.UTF_8)
            );
            zout.closeEntry();
            entry = new ZipEntry("booking_stat.csv");
            zout.putNextEntry(entry);
            csvBuilder = new StringBuilder("booking_id,user_id,check_in,check_out");
            zout.write(
                    bookingStatRepository.findAll().stream()
                            .map(BookingStat::toString)
                            .reduce(
                                    csvBuilder,
                                    StringBuilder::append,
                                    StringBuilder::append
                            )
                            .toString()
                            .getBytes(StandardCharsets.UTF_8)
            );
            zout.closeEntry();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
