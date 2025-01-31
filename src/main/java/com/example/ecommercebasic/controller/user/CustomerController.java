package com.example.ecommercebasic.controller.user;

import com.example.ecommercebasic.dto.user.CustomerRequestDto;
import com.example.ecommercebasic.entity.user.Customer;
import com.example.ecommercebasic.service.user.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<String> createCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        return new ResponseEntity<>(customerService.createCustomer(customerRequestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return new ResponseEntity<>(customerService.findAllCustomers(),HttpStatus.OK);
    }
}
