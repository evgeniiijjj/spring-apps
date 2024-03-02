package com.example.tasktracker.enums;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum RoleType {
    ROLE_MANAGER,
    ROLE_USER;

    public GrantedAuthority toAuthority() {
        return new SimpleGrantedAuthority(this.name());
    }
}
