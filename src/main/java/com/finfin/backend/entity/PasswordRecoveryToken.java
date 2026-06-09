package com.finfin.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
public class PasswordRecoveryToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotBlank
    private User user;

    private UUID token;

    private LocalDateTime expirationTime = LocalDateTime.now().plusHours(1);

    private boolean isUsed;
}
