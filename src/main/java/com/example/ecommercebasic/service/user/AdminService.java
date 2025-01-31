package com.example.ecommercebasic.service.user;

import com.example.ecommercebasic.builder.user.AdminBuilder;
import com.example.ecommercebasic.dto.user.AdminRequestDto;
import com.example.ecommercebasic.entity.user.Admin;
import com.example.ecommercebasic.repository.user.AdminRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final AdminBuilder adminBuilder;
    public AdminService(AdminRepository adminRepository, AdminBuilder adminBuilder) {
        this.adminRepository = adminRepository;
        this.adminBuilder = adminBuilder;
    }

    public String createAdmin(AdminRequestDto adminRequestDto) {
        Admin admin = adminBuilder.AdminRequestDtoToAdmin(adminRequestDto);
        adminRepository.save(admin);
        return "Success";
    }

    public List<Admin> findAllAdmins() {
        return adminRepository.findAll();
    }
}
