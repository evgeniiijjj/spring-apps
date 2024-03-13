package com.example.bookingservice.service.stat;

import com.example.bookingservice.entity.stat.BookingStat;
import com.example.bookingservice.entity.stat.UserStat;
import com.example.bookingservice.repository.stat.BookingStatRepository;
import com.example.bookingservice.repository.stat.UserStatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RequiredArgsConstructor
@Service
public class StatService {
    private final UserStatRepository userStatRepository;
    private final BookingStatRepository bookingStatRepository;

    public void saveRegistration(UserStat userStat) {
        userStatRepository.save(userStat);
    }

    public void saveBooking(BookingStat bookingStat) {
        bookingStatRepository.save(bookingStat);
    }

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
