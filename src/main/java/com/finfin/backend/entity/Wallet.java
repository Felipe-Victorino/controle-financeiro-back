package com.finfin.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    private User owner;

    @NotBlank
    @Size(min = 2, max = 321)
    private String name;

    @Size(max = 255)
    private String description;

    private LocalDateTime createdIn = LocalDateTime.now();

    @OneToMany(mappedBy = "wallet_member")
    private List<WalletMember> members;

    @OneToMany(mappedBy = "transaction")
    private List<Transaction> transactions;
}
