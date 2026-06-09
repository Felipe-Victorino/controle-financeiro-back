package com.finfin.backend.entity;

import com.finfin.backend.entity.enums.RoleWallet;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumeratedValue;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity

public class WalletMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotBlank
    private Wallet wallet;

    @ManyToOne
    @NotBlank
    private User user;

    @NotBlank
    @EnumeratedValue
    private RoleWallet role;

    private LocalDateTime joinedIn = LocalDateTime.now();
}
