package com.example.ecommercebasic.repository.user;

import com.example.ecommercebasic.entity.user.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Integer> {
}
