package com.example.bookingservice.entity.stat;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.MessageFormat;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Document(collection = "users")
public class UserStat {
    @Id
    @Indexed(unique = true)
    private Long id;
    private String userName;
    private Instant registrationTime;

    @Override
    public String toString() {
        return MessageFormat.format(",{0},{1},{2}", id, userName, registrationTime);
    }
}
