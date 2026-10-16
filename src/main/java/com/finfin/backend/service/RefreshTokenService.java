package com.finfin.backend.service;

import com.finfin.backend.entity.RefreshToken;
import com.finfin.backend.entity.User;
import com.finfin.backend.repository.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwt.refresh.expiration}")
    private Long expiration;

    @Autowired
    private RefreshTokenRepository repository;

    public RefreshToken create(User user) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpirationDate(LocalDateTime.now().plus(Duration.ofMillis(expiration)));
        refreshToken.setRevoked(false);
        return repository.save(refreshToken);
    }

    public RefreshToken validate(String token) {
        RefreshToken refreshToken = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Refresh token inválido"));
        if (refreshToken.isRevoked() || refreshToken.getExpirationDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Refresh token expirado ou revogado");
        }
        return refreshToken;
    }

    public RefreshToken rotate(RefreshToken oldToken) {
        oldToken.setRevoked(true);
        repository.save(oldToken);
        return create(oldToken.getUser());
    }

    public void revoke(String token) {
        repository.findByToken(token).ifPresent(rt -> {
            rt.setRevoked(true);
            repository.save(rt);
        });
    }
}
