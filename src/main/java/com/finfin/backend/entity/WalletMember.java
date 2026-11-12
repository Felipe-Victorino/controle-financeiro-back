package com.finfin.backend.entity;

import com.finfin.backend.entity.enums.RoleWallet;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "wallet_member")
@Table(name = "wallet_member",
        uniqueConstraints = @UniqueConstraint(columnNames = {"wallet_id", "user_id"}))
public class WalletMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallet_member_id")
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleWallet role;

    @PastOrPresent
    private LocalDateTime joinedIn = LocalDateTime.now();
}
