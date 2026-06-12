package com.finfin.backend.entity;

import com.finfin.backend.entity.enums.RoleWallet;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumeratedValue;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "wallet_member")
public class WalletMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotBlank
    @JoinColumn(name = "id_wallet")
    private Wallet wallet;

    @ManyToOne
    @NotBlank
    @JoinColumn(name = "id_user")
    private User user;

    @NotBlank
    @EnumeratedValue
    @Pattern(regexp = "OWNER|EDITOR|VIEWER")
    private RoleWallet role;

    private LocalDateTime joinedIn = LocalDateTime.now();
}
