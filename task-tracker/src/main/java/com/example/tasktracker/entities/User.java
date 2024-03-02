package com.example.tasktracker.entities;

import com.example.tasktracker.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "users")
public class User {
    @Id
    @Indexed(unique = true)
    private String email;
    private String userName;
    private String password;
    @Field("roles")
    private Set<RoleType> roles = new HashSet<>();

    public User addRole(RoleType role) {
        roles.add(role);
        return this;
    }
}
