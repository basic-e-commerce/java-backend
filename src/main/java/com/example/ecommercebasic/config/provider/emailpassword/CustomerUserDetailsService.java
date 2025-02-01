package com.example.ecommercebasic.config.provider.emailpassword;

import com.example.ecommercebasic.entity.user.Customer;
import com.example.ecommercebasic.entity.user.User;
import com.example.ecommercebasic.service.user.CustomerService;
import com.example.ecommercebasic.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    private final CustomerService customerService;

    public CustomerUserDetailsService( CustomerService customerService) {
        this.customerService = customerService;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Customer customer = customerService.findByUsername(username);

        return new org.springframework.security.core.userdetails.User(
                customer.getUsername(),
                customer.getPassword(),
                customer.getAuthorities()
        );
    }

}
