package com.finfin.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "wallet_id")
    private UUID id;

    @ManyToOne
    @NotNull(message = "{wallet.owner.needed}")
    @JoinColumn(name = "user_id")
    private User owner;

    @NotNull(message = "{wallet.name.required}")
    @Size(min = 2, max = 128)
    private String name;

    @Size(max = 255)
    private String description;

    @PastOrPresent
    private LocalDateTime createdIn = LocalDateTime.now();

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<WalletMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "wallet", cascade = CascadeType.REMOVE)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Transaction> transactions = new ArrayList<>();
}
