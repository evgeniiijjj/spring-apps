package com.example.bookingservice.services.stat;

import com.example.bookingservice.entities.stat.BookingStat;
import com.example.bookingservice.entities.stat.UserStat;

import java.io.OutputStream;

public interface StatService {
    void saveRegistration(UserStat userStat);

    void saveBooking(BookingStat bookingStat);

    void sendStat(OutputStream outputStream);
}
