package com.example.ecommercebasic.repository.auth;

import com.example.ecommercebasic.entity.auth.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
    Optional<RefreshToken> findByRefreshTokenHash(String token);
}
