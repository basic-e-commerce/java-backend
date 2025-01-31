package com.example.ecommercebasic.builder.user;

import com.example.ecommercebasic.dto.user.AdminRequestDto;
import com.example.ecommercebasic.entity.user.Admin;
import com.example.ecommercebasic.entity.user.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminBuilder {
    private final PasswordEncoder passwordEncoder;

    public AdminBuilder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public Admin AdminRequestDtoToAdmin(AdminRequestDto adminRequestDto) {
        return new Admin(
                adminRequestDto.getFirstName(),
                adminRequestDto.getLastName(),
                adminRequestDto.getUsername(),
                passwordEncoder.encode(adminRequestDto.getPassword()),
                Set.of(Roles.ROLE_CUSTOMER),
                false,
                false
        );
    }
}
