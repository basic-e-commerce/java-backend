package com.example.ecommercebasic.service.user;

import com.example.ecommercebasic.builder.user.CustomerBuilder;
import com.example.ecommercebasic.config.validation.RegexValidation;
import com.example.ecommercebasic.constant.ApplicationConstant;
import com.example.ecommercebasic.dto.user.CustomerRequestDto;
import com.example.ecommercebasic.entity.user.Customer;
import com.example.ecommercebasic.exception.InvalidFormatException;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.exception.ResourceAlreadyExistException;
import com.example.ecommercebasic.repository.user.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerBuilder customerBuilder;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerRepository customerRepository, CustomerBuilder customerBuilder, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.customerBuilder = customerBuilder;
        this.passwordEncoder = passwordEncoder;
    }

    public String createCustomer(CustomerRequestDto customerRequestDto) {

        if (!RegexValidation.isValidEmail(customerRequestDto.getUsername()))
            throw new InvalidFormatException("Invalid email address");

        if (!RegexValidation.isValidPasswword(customerRequestDto.getPassword()))
            throw new InvalidFormatException("Invalid password");

        if (existByUsername(customerRequestDto.getUsername()))
            throw new ResourceAlreadyExistException("Username already exists");

        Customer customer = customerBuilder.CustomerRequestDtoToCustomer(customerRequestDto);
        customer.setPassword(passwordEncoder.encode(customerRequestDto.getPassword()));
        customerRepository.save(customer);
        return "Successfully";
    }

    private boolean existByUsername(String username) {
        return customerRepository.existsByUsername(username);
    }


    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findByUsername(String username) {
        return customerRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(ApplicationConstant.NOT_FOUND));
    }
}
