package com.finfin.backend.repository;

import com.finfin.backend.entity.PasswordRecoveryToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PasswordRecoveryTokenRepository extends JpaRepository<PasswordRecoveryToken, Long> {

    @Query("SELECT password_recovery_token from password_recovery_token prt where prt.token = ?1")
    public PasswordRecoveryToken getPasswordRecoveryTokenByToken(UUID token);

    PasswordRecoveryToken findByToken(UUID token);
}
