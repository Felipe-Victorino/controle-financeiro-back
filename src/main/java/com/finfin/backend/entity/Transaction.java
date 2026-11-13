package com.finfin.backend.entity;

import com.finfin.backend.entity.enums.TransactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.FutureOrPresent;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.validation.constraints.Positive;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @ManyToOne
    @NotNull(message = "{transaction.wallet.needed}")
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @NotNull(message = "{transaction.createdBy.needed}")
    @JoinColumn(name = "user_id")
    private User createdBy;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @NotNull(message = "{transaction.value.needed}")
    @Digits(integer = 5, fraction = 2)
    @Digits(integer = 13, fraction = 2)
    @Column(precision = 15, scale = 2, name = "amount")
    private BigDecimal value;

    @Size(max = 255)
    private String description;

    @NotNull
    @PastOrPresent
    private LocalDate dateTransaction;

    @PastOrPresent
    private LocalDateTime createdIn = LocalDateTime.now();


}
