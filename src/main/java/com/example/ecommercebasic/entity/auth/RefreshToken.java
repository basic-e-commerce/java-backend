package com.example.ecommercebasic.entity.auth;

import com.example.ecommercebasic.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // Token'ın benzersiz ID'si

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // User ile ilişki kuruyoruz
    private User user;  // Kullanıcı ID'si (veya username)

    @Column(nullable = false, unique = true)
    private String refreshTokenHash;  // Refresh token'ı

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createTime;  // Token oluşturulma zamanı

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expirationTime;  // Token'ın son kullanma zamanı

    @Column(nullable = false)
    private boolean isActive;  // Token aktif mi?

    public RefreshToken() {
    }

    public RefreshToken(User user, String refreshTokenHash, LocalDateTime expirationTime) {
        this.user = user;
        this.refreshTokenHash = refreshTokenHash;
        this.isActive = true;
        this.expirationTime = expirationTime;
        this.createTime = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getRefreshTokenHash() {
        return refreshTokenHash;
    }

    public void setRefreshTokenHash(String refreshToken) {
        this.refreshTokenHash = refreshToken;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
