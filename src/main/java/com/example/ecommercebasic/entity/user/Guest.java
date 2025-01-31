package com.example.ecommercebasic.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import javax.management.relation.Role;
import java.util.Set;

@Entity
@Table(name = "guests")
public class Guest extends User {
    private String sessionId;

    public Guest(String sessionId) {
        this.sessionId = sessionId;
    }

    public Guest() {
    }
    public String getSessionId() {
        return sessionId;
    }
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}
