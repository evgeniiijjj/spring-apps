package com.example.bookingservice.dto;

import com.example.bookingservice.entity.Hotel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class HotelCriteria {
    private String name;
    private String adTitle;
    private String city;
    private String address;
    private Integer cityCenterDistance;
    private Float rating;
    private Integer numberOfRatings;

    public Specification<Hotel> spec() {
        return Specification.allOf(
                Arrays.stream(HotelCriteria.class.getDeclaredFields())
                        .map(this::getSpecification)
                        .filter(Objects::nonNull)
                        .toList()
        );
    }

    public Specification<Hotel> getSpecification(Field field) {
        try {
            return HotelCriteria.getSpecification(field.getName(), field.get(this));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Specification<Hotel> getSpecification(String name, Object value) {
        if (value == null) {
            return null;
        }
        return (root, cq, cb) -> cb.equal(root.get(name), value);
    }
}
