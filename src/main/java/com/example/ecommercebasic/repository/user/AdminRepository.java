package com.example.ecommercebasic.repository.user;

import com.example.ecommercebasic.entity.user.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
