package com.example.ecommercebasic.controller.user;

import com.example.ecommercebasic.dto.user.AddressRequestDto;
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

    @GetMapping("/by-id")
    public ResponseEntity<Customer> getCustomerById(@RequestParam int id) {
        return new ResponseEntity<>(customerService.findById(id),HttpStatus.OK);
    }

    @PutMapping("/add-address")
    public ResponseEntity<Customer> addAddress(@RequestBody AddressRequestDto addressRequestDto) {
        return new ResponseEntity<>(customerService.addAddress(addressRequestDto),HttpStatus.OK);
    }

    @PutMapping("/update-address")
    public ResponseEntity<String > updateAddress(@RequestBody AddressRequestDto addressRequestDto, @RequestParam int addressId) {
        return new ResponseEntity<>(customerService.updateAddress(addressRequestDto,addressId),HttpStatus.OK);
    }

    @DeleteMapping("/delete-address")
    public ResponseEntity<String> deleteAddress(@RequestParam int addressId) {
        return new ResponseEntity<>(customerService.deleteAddress(addressId),HttpStatus.OK);
    }


}
