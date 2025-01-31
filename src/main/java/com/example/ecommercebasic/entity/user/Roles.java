package com.example.ecommercebasic.entity.user;

import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Map;

public enum Roles implements GrantedAuthority {
    ROLE_CUSTOMER("CUSTOMER"),
    ROLE_ADMIN("ADMIN"), ROLE_GUEST("GUEST");

    private static final Map<String, Roles> ROLE_MAP = new HashMap<>();

    private final String role;

    // Enum sabitlerini bir haritaya ekleyerek başlatıyoruz.
    static {
        for (Roles r : Roles.values()) {
            ROLE_MAP.put(r.role, r);
        }
    }

    Roles(String role) {
        this.role = role;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public static Roles getRoleByName(String roleName) {
        return ROLE_MAP.get(roleName.toUpperCase());
    }
}
