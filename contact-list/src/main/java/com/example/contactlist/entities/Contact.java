package com.example.contactlist.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contact {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
