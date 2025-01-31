package com.example.ecommercebasic.builder.user;

import com.example.ecommercebasic.dto.user.AdminRequestDto;
import com.example.ecommercebasic.entity.user.Admin;
import com.example.ecommercebasic.entity.user.Roles;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AdminBuilder {


    public Admin AdminRequestDtoToAdmin(AdminRequestDto adminRequestDto) {
        return new Admin(
                adminRequestDto.getFirstName(),
                adminRequestDto.getLastName(),
                adminRequestDto.getUsername(),
                adminRequestDto.getPassword(),
                Set.of(Roles.ROLE_ADMIN),
                false,
                false
        );
    }
}
