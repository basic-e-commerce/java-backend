package com.example.ecommercebasic.service.user;

import com.example.ecommercebasic.builder.user.CustomerBuilder;
import com.example.ecommercebasic.dto.user.CustomerRequestDto;
import com.example.ecommercebasic.entity.user.Customer;
import com.example.ecommercebasic.repository.user.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerBuilder customerBuilder;

    public CustomerService(CustomerRepository customerRepository, CustomerBuilder customerBuilder) {
        this.customerRepository = customerRepository;
        this.customerBuilder = customerBuilder;
    }

    public String createCustomer(CustomerRequestDto customerRequestDto) {
        Customer customer = customerBuilder.CustomerRequestDtoToCustomer(customerRequestDto);
        customerRepository.save(customer);
        return "Successfully";
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }
}
