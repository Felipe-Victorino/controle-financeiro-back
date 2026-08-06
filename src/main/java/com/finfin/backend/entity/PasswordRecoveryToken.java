package com.finfin.backend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity(name ="password_recovery_token")
public class PasswordRecoveryToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "password_recovery_token_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @org.hibernate.validator.constraints.UUID
    private UUID token = UUID.randomUUID();

    @Future(message = "Token inválido")
    private LocalDateTime expirationTime = LocalDateTime.now().plusHours(1);

    private boolean isUsed = false;



}
