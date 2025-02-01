package com.example.ecommercebasic.controller.user;

import com.example.ecommercebasic.dto.user.auth.AuthenticationRequestDto;
import com.example.ecommercebasic.dto.user.auth.AuthenticationResponseDto;
import com.example.ecommercebasic.service.auth.AuthenticationService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/c-login")
    public ResponseEntity<AuthenticationResponseDto> loginCustomer(@RequestBody AuthenticationRequestDto authenticationRequestDto, HttpServletResponse response) {
        return new ResponseEntity<>(authenticationService.loginCustomer(authenticationRequestDto,response), HttpStatus.OK);
    }

    @PostMapping("/a-login")
    public ResponseEntity<AuthenticationResponseDto> loginAdmin(@RequestBody AuthenticationRequestDto authenticationRequestDto){
        return new ResponseEntity<>(authenticationService.loginAdmin(authenticationRequestDto), HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponseDto> refresh(@CookieValue(name = "refresh_token")String refreshToken){
        return new ResponseEntity<>(authenticationService.refresh(refreshToken),HttpStatus.OK);
    }

    @PostMapping("/ref")
    public ResponseEntity<String> ref(@CookieValue(name = "refresh_token")String refreshToken){
        return new ResponseEntity<>("send ref: "+refreshToken,HttpStatus.OK);
    }
}
