package com.example.ecommercebasic.service.auth;

import com.example.ecommercebasic.config.validation.RegexValidation;
import com.example.ecommercebasic.constant.ApplicationConstant;
import com.example.ecommercebasic.dto.user.auth.AuthenticationRequestDto;
import com.example.ecommercebasic.dto.user.auth.AuthenticationResponseDto;
import com.example.ecommercebasic.entity.auth.RefreshToken;
import com.example.ecommercebasic.entity.user.Roles;
import com.example.ecommercebasic.exception.InvalidFormatException;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.exception.TokenExpiredException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

@Service
public class AuthenticationService {

    @Value("${cookie.refreshTokenCookie.secure}")
    private boolean secure;
    @Value("${cookie.refreshTokenCookie.sameSite}")
    private String sameSite;
    @Value("${cookie.refreshTokenCookie.path}")
    private String path;
    @Value("${cookie.refreshTokenCookie.refreshmaxAge}")
    private String maxAge;

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RegexValidation regexValidation;
    private final RefreshTokenService refreshTokenService;

    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtils jwtUtils, RegexValidation regexValidation, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.regexValidation = regexValidation;
        this.refreshTokenService = refreshTokenService;
    }


    public AuthenticationResponseDto loginCustomer(AuthenticationRequestDto authenticationRequestDto, HttpServletResponse response) {

        /**
        // Giriş bilgilerinin formatını kontrol et
        if (regexValidation.isValidEmail(authenticationRequestDto.getUsername()) && regexValidation.isValidPassword(authenticationRequestDto.getPassword())) {
            throw new InvalidFormatException(ApplicationConstant.INVALID_FORMAT);
        }**/

        // Authentication nesnesi oluşturuluyor
        UsernamePasswordAuthenticationToken authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                authenticationRequestDto.getUsername(),
                authenticationRequestDto.getPassword()
        );
        authenticationRequest.setDetails(Roles.ROLE_CUSTOMER);

        // Kimlik doğrulama işlemi
        Authentication authenticatedUser = authenticationManager.authenticate(authenticationRequest);

        // Eğer kullanıcı doğrulanamazsa hata fırlat
        if (!authenticatedUser.isAuthenticated())
            throw new NotFoundException(ApplicationConstant.WRONG_CREDENTIALS);


        String accessToken = jwtUtils.generateAccessToken(authenticatedUser.getName());
        String refreshToken = jwtUtils.generateRefreshToken(authenticatedUser.getName());

        try {
            String refreshHash = jwtUtils.hashToken(refreshToken);
            String hash = refreshTokenService.createRefreshToken(authenticatedUser.getName(),refreshHash);
            // Set-Cookie başlığı ile cookie'yi gönder
            response.addHeader("Set-Cookie", "refresh_token=" + hash
                    + "; Path=" + path
                    + "; HttpOnly"
                    + "; Secure=" + secure
                    + "; Max-Age=" + Integer.parseInt(maxAge)
                    + "; SameSite=" + sameSite);

            return new AuthenticationResponseDto(accessToken);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public AuthenticationResponseDto loginAdmin(AuthenticationRequestDto authenticationRequestDto,HttpServletResponse response) {
        /**
        // Giriş bilgilerinin formatını kontrol et
        if (regexValidation.isValidEmail(authenticationRequestDto.getUsername()) && regexValidation.isValidPassword(authenticationRequestDto.getPassword())) {
            throw new InvalidFormatException(ApplicationConstant.INVALID_FORMAT);
        } **/

        // Authentication nesnesi oluşturuluyor
        UsernamePasswordAuthenticationToken authenticationRequest = UsernamePasswordAuthenticationToken.unauthenticated(
                authenticationRequestDto.getUsername(),
                authenticationRequestDto.getPassword()
        );
        authenticationRequest.setDetails(Roles.ROLE_ADMIN);
        // Kimlik doğrulama işlemi
        Authentication authenticatedUser = authenticationManager.authenticate(authenticationRequest);

        // Eğer kullanıcı doğrulanamazsa hata fırlat
        if (!authenticatedUser.isAuthenticated())
            throw new NotFoundException(ApplicationConstant.WRONG_CREDENTIALS);


        String accessToken = jwtUtils.generateAccessToken(authenticatedUser.getName());
        String refreshToken = jwtUtils.generateRefreshToken(authenticatedUser.getName());

        try {
            String refreshHash = jwtUtils.hashToken(refreshToken);
            String hash = refreshTokenService.createRefreshToken(authenticatedUser.getName(),refreshHash);
            // Set-Cookie başlığı ile cookie'yi gönder
            response.addHeader("Set-Cookie", "refresh_token=" + hash
                    + "; Path=" + path
                    + "; HttpOnly"
                    + "; Secure=" + secure
                    + "; Max-Age=" + Integer.parseInt(maxAge)
                    + "; SameSite=" + sameSite);

            return new AuthenticationResponseDto(accessToken);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthenticationResponseDto refresh(String refreshTokenHash) {
        RefreshToken refreshToken = refreshTokenService.getRefreshTokenHash(refreshTokenHash);

        if (refreshToken.isActive() && refreshToken.getExpirationTime().isBefore(LocalDateTime.now())) {
            return  new AuthenticationResponseDto(jwtUtils.generateAccessToken(refreshToken.getUser().getUsername()));
        }else
            throw new TokenExpiredException(ApplicationConstant.TRY_LOGIN);

    }
}
