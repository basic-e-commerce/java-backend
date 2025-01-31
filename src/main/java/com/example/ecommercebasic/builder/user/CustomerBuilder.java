package com.example.ecommercebasic.builder.user;

import com.example.ecommercebasic.dto.user.CustomerRequestDto;
import com.example.ecommercebasic.entity.user.Customer;
import com.example.ecommercebasic.entity.user.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CustomerBuilder {

    public Customer CustomerRequestDtoToCustomer(CustomerRequestDto customerRequestDto) {
        return new Customer(
                customerRequestDto.getFirstName(),
                customerRequestDto.getLastName(),
                customerRequestDto.getUsername(),
                customerRequestDto.getPassword(),
                Set.of(Roles.ROLE_CUSTOMER),
                false,
                false
        );
    }
}
