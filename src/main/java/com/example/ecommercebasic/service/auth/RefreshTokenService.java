package com.example.ecommercebasic.service.auth;

import com.example.ecommercebasic.constant.ApplicationConstant;
import com.example.ecommercebasic.entity.auth.RefreshToken;
import com.example.ecommercebasic.entity.user.User;
import com.example.ecommercebasic.exception.NotFoundException;
import com.example.ecommercebasic.repository.auth.RefreshTokenRepository;
import com.example.ecommercebasic.service.user.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class RefreshTokenService {
    private final UserService userService;
    private final RefreshTokenRepository refreshTokenRepository;
    @Value("${jwt.refreshAge}")
    private int refreshExp;


    public RefreshTokenService(UserService userService, RefreshTokenRepository refreshTokenRepository) {
        this.userService = userService;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String createRefreshToken(String username,String refreshTokenHash) {
        User user = userService.getUserByUsername(username);
        RefreshToken refreshToken = new RefreshToken(user,refreshTokenHash, LocalDateTime.now().plusNanos(refreshExp));
        refreshTokenRepository.save(refreshToken);
        return refreshTokenHash;
    }

    public RefreshToken getRefreshTokenHash(String refreshTokenHash) {
        return refreshTokenRepository.findByRefreshTokenHash(refreshTokenHash).orElseThrow(()-> new NotFoundException(ApplicationConstant.NOT_FOUND));
    }
}
