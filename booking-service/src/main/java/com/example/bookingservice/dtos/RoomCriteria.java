package com.example.bookingservice.dtos;

import com.example.bookingservice.entities.Booking;
import com.example.bookingservice.entities.Hotel;
import com.example.bookingservice.entities.Room;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RoomCriteria {
    private String name;
    private String description;
    private Integer minPrice;
    private Integer maxPrice;
    private Integer capacity;
    private Instant checkIn;
    private Instant checkOut;
    private Long hotelId;

    public Specification<Room> spec() {
        return getSpecification(this);
    }

    public static Specification<Room> getSpecification(RoomCriteria criteria) {
        Map<String, Object> fields = new HashMap<>();
        Arrays.stream(RoomCriteria.class.getDeclaredFields()).forEach(field -> {
            try {
                fields.put(field.getName(), field.get(criteria));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return Specification.allOf(
                fields.keySet().stream()
                        .map(fieldName -> RoomCriteria.getSpecification(fieldName, fields))
                        .filter(Objects::nonNull)
                        .toList()
        );
    }

    public static Specification<Room> getSpecification(String fieldName, Map<String, Object> fields) {
        if (fields.get(fieldName) == null) {
            return null;
        }
        if (fieldName.contains("min")) {
            return (root, cq, cb) -> cb.greaterThanOrEqualTo(root.get("price"), (Integer) fields.get(fieldName));
        }
        if (fieldName.contains("max")) {
            return (root, cq, cb) -> cb.lessThanOrEqualTo(root.get("price"), (Integer) fields.get(fieldName));
        }
        if (fieldName.equals("hotelId")) {
            Hotel hotel = new Hotel();
            hotel.setId((Long) fields.get(fieldName));
            return (root, cq, cb) -> cb.equal(root.get("hotel"), hotel);
        }
        if (fieldName.equals("checkIn")) {
            if (fields.get("checkOut") == null) {
                return null;
            }
            return (root, cq, cb) -> {
                Join<Room, Booking> joinBookings = root.join("bookings", JoinType.LEFT);
                return cb.or(
                        cb.isEmpty(root.get("bookings")),
                        cb.and(
                                cb.not(cb.between(joinBookings.get("checkIn"), (Instant) fields.get("checkIn"), (Instant) fields.get("checkOut"))),
                                cb.not(cb.between(joinBookings.get("checkOut"), (Instant) fields.get("checkIn"), (Instant) fields.get("checkOut"))),
                                cb.or(
                                        cb.lessThan(joinBookings.get("checkOut"), (Instant) fields.get("checkIn")),
                                        cb.greaterThan(joinBookings.get("checkIn"), (Instant) fields.get("checkOut"))
                                )
                        )
                );
            };
        }
        if (fieldName.equals("checkOut")) {
            return null;
        }
        return (root, cq, cb) -> cb.equal(root.get(fieldName), fields.get(fieldName));
    }
}
