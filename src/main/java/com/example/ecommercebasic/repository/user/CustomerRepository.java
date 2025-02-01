package com.example.ecommercebasic.repository.user;

import com.example.ecommercebasic.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByUsername(String username);
    boolean existsByUsername(String username);
}
