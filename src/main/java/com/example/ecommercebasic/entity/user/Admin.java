package com.example.ecommercebasic.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "admin")
public class Admin extends User {

    public Admin(String firstName, String lastName, String username, String password, Set<Roles> authorities, boolean enabled, boolean accountNonLocked) {
        super(firstName, lastName, username, password, authorities, enabled, accountNonLocked);
    }

    public Admin() {}
}
