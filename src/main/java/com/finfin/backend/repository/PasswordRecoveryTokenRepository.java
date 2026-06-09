package com.finfin.backend.repository;

import com.finfin.backend.entity.PasswordRecoveryToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRecoveryTokenRepository extends JpaRepository<PasswordRecoveryToken, Long> {
}
