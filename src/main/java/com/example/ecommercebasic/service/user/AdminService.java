package com.example.ecommercebasic.service.user;

import com.example.ecommercebasic.builder.user.AdminBuilder;
import com.example.ecommercebasic.dto.user.AdminRequestDto;
import com.example.ecommercebasic.entity.user.Admin;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.repository.user.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final AdminBuilder adminBuilder;
    private final PasswordEncoder passwordEncoder;

    public AdminService(AdminRepository adminRepository, AdminBuilder adminBuilder, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.adminBuilder = adminBuilder;
        this.passwordEncoder = passwordEncoder;
    }

    public String createAdmin(AdminRequestDto adminRequestDto) {
        Admin admin = adminBuilder.AdminRequestDtoToAdmin(adminRequestDto);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        adminRepository.save(admin);
        return "Success";
    }

    public List<Admin> findAllAdmins() {
        return adminRepository.findAll();
    }

    public Admin findByUsername(String username) {
        return adminRepository.findByUsername(username).orElseThrow(()-> new NotFoundException("User Not Found"));
    }
}
