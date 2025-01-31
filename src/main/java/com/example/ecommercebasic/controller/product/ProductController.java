package com.example.ecommercebasic.controller.product;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    @GetMapping("/secure")
    public String secure() {
        return "secure";
    }


    @GetMapping("/not-secure")
    public String notsecure() {
        return "notsecure";
    }
}
