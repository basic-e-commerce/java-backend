package com.example.ecommercebasic.controller.user;

import com.example.ecommercebasic.dto.user.AddressRequestDto;
import com.example.ecommercebasic.entity.Address;
import com.example.ecommercebasic.service.user.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
}
