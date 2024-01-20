package org.example.entities;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Contact implements Comparable<Contact> {

    private static int counter;
    private Integer id;
    private String fullName;
    private String phoneNumber;
    private String email;

    private Integer numInOrder;

    public Contact(String fullName,
                   String phoneNumber,
                   String email) {
        id = ++counter;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Contact(String[] contactPart) {
        this(
                contactPart[0],
                contactPart[1],
                contactPart[2]
        );
    }

    @Override
    public int compareTo(Contact o) {

        return id.compareTo(o.id);
    }
}
