package com.example.ecommercebasic.repository.user;

import com.example.ecommercebasic.entity.Address;
import com.example.ecommercebasic.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findByUser(User user);
}
