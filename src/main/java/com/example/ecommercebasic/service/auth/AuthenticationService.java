package com.example.ecommercebasic.service.auth;

import com.example.ecommercebasic.config.validation.RegexValidation;
import com.example.ecommercebasic.constant.ApplicationConstant;
import com.example.ecommercebasic.dto.user.auth.AuthenticationRequestDto;
import com.example.ecommercebasic.dto.user.auth.AuthenticationResponseDto;
import com.example.ecommercebasic.entity.user.Roles;
import com.example.ecommercebasic.exception.InvalidFormatException;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.service.user.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

    public AuthenticationService(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }


    public AuthenticationResponseDto loginCustomer(AuthenticationRequestDto authenticationRequestDto, HttpServletResponse response) {
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

        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath(path); // Yalnızca bu endpoint'e gönderilecek
        refreshTokenCookie.setMaxAge(Integer.parseInt(maxAge)); // 7 gün boyunca geçerli
        refreshTokenCookie.setSecure(secure); // Geliştirme aşamasında olduğu için false

        response.addCookie(refreshTokenCookie);
        response.addHeader("Set-Cookie", "refresh_token=" + refreshToken + "; Path=/api/v1/auth/refresh; HttpOnly; Secure=false; SameSite="+sameSite);

        return new AuthenticationResponseDto(accessToken);
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

        return new AuthenticationResponseDto(accessToken);
    }

    public AuthenticationResponseDto refresh(String refreshToken) {
        return new AuthenticationResponseDto(refreshToken);
    }
}
