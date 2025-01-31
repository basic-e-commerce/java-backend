package com.example.ecommercebasic.service.auth;

import com.example.ecommercebasic.config.validation.RegexValidation;
import com.example.ecommercebasic.constant.ApplicationConstant;
import com.example.ecommercebasic.dto.user.auth.AuthenticationRequestDto;
import com.example.ecommercebasic.dto.user.auth.AuthenticationResponseDto;
import com.example.ecommercebasic.entity.user.Roles;
import com.example.ecommercebasic.exception.InvalidFormatException;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.service.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }


    public AuthenticationResponseDto loginCustomer(AuthenticationRequestDto authenticationRequestDto) {
        /*
        // Giriş bilgilerinin formatını kontrol et
        if (!(RegexValidation.isValidEmail(authenticationRequestDto.getUsername()) && RegexValidation.isValidPasswword(authenticationRequestDto.getPassword()))) {
            throw new InvalidFormatException("Geçersiz format");
        }*/

        // Authentication nesnesi oluşturuluyor
        UsernamePasswordAuthenticationToken authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                authenticationRequestDto.getUsername(),
                authenticationRequestDto.getPassword()
        );
        authenticationRequest.setDetails(Roles.ROLE_CUSTOMER);

        // Kimlik doğrulama işlemi
        Authentication authenticatedUser = authenticationManager.authenticate(authenticationRequest);

        // Eğer kullanıcı doğrulanamazsa hata fırlat
        if (!authenticatedUser.isAuthenticated()) {
            throw new NotFoundException(ApplicationConstant.WRONG_CREDENTIALS);
        }

        String accessToken = jwtUtils.generateAccessToken(authenticatedUser.getName());
        String refreshToken = jwtUtils.generateRefreshToken(authenticatedUser.getName());

        return new AuthenticationResponseDto(accessToken, refreshToken);
    }

    public AuthenticationResponseDto loginAdmin(AuthenticationRequestDto authenticationRequestDto) {
        // Authentication nesnesi oluşturuluyor
        // Authentication nesnesi oluşturuluyor
        UsernamePasswordAuthenticationToken authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                authenticationRequestDto.getUsername(),
                authenticationRequestDto.getPassword()
        );
        authenticationRequest.setDetails(Roles.ROLE_ADMIN);
        // Kimlik doğrulama işlemi
        Authentication authenticatedUser = authenticationManager.authenticate(authenticationRequest);

        // Eğer kullanıcı doğrulanamazsa hata fırlat
        if (!authenticatedUser.isAuthenticated()) {
            throw new NotFoundException(ApplicationConstant.WRONG_CREDENTIALS);
        }

        String accessToken = jwtUtils.generateAccessToken(authenticatedUser.getName());
        String refreshToken = jwtUtils.generateRefreshToken(authenticatedUser.getName());

        return new AuthenticationResponseDto(accessToken, refreshToken);
    }
}
