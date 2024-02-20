package com.example.tasktracker.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDto implements Comparable<UserDto> {
    private String id;
    private String userName;
    private String email;

    @Override
    public int compareTo(UserDto o) {
        return userName.compareTo(o.userName);
    }
}
