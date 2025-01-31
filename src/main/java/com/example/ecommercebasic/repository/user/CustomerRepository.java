package com.example.ecommercebasic.repository.user;

import com.example.ecommercebasic.entity.user.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
}
