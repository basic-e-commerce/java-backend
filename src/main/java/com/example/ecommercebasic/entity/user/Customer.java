package com.example.ecommercebasic.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table
public class Customer extends User {

    public Customer(String firstName, String lastName, String username, String password, Set<Roles> authorities, boolean enabled, boolean accountNonLocked) {
        super(firstName, lastName, username, password, authorities, enabled, accountNonLocked);
    }

    public Customer() {}

}
