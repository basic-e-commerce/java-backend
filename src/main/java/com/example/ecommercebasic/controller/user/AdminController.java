package com.example.ecommercebasic.controller.user;

import com.example.ecommercebasic.dto.user.AdminRequestDto;
import com.example.ecommercebasic.dto.user.CustomerRequestDto;
import com.example.ecommercebasic.entity.user.Admin;
import com.example.ecommercebasic.service.user.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    public ResponseEntity<String> createAdmin(@RequestBody AdminRequestDto adminRequestDto) {
        return new ResponseEntity<>(adminService.createAdmin(adminRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Admin>> getAdmins() {
        return new ResponseEntity<>(adminService.findAllAdmins(),HttpStatus.OK);
    }
}
