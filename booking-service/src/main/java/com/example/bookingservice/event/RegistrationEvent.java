package com.example.bookingservice.event;

import com.example.bookingservice.entity.stat.UserStat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationEvent implements Event<UserStat> {
    private Long userId;
    private String userName;
    private Instant registrationTime;

    @Override
    public UserStat getStatEntity() {
        return new UserStat(userId, userName, registrationTime);
    }
}
