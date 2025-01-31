package com.example.ecommercebasic.config.provider.emailpassword;

import com.example.ecommercebasic.entity.user.Admin;
import com.example.ecommercebasic.entity.user.Customer;
import com.example.ecommercebasic.service.user.AdminService;
import com.example.ecommercebasic.service.user.CustomerService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminUserDetailsService implements UserDetailsService {
    private final AdminService adminService;

    public AdminUserDetailsService(AdminService adminService) {
        this.adminService = adminService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminService.findByUsername(username);

        return new org.springframework.security.core.userdetails.User(
                admin.getUsername(),
                admin.getPassword(),
                admin.getAuthorities()
        );
    }

}
