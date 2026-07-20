package com.finfin.backend.entity;

import com.finfin.backend.entity.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumeratedValue;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
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
    private Long id;

    @ManyToOne
    @NotBlank(message = "{transaction.wallet.needed}")
    @JoinColumn(name = "id_wallet")
    private Wallet wallet;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @ManyToOne
    @NotBlank(message = "{transaction.createdBy.needed}")
    @JoinColumn(name = "id_user")
    private User createdBy;

    @EnumeratedValue
    @Pattern(regexp = "RECEIPT|EXPENSE")
    private TransactionType type;

    @NotBlank(message = "{transaction.value.needed}")
    @Positive
    @DecimalMin("0.01")
    private BigDecimal value;

    @Size(max = 255)
    private String description;

    @PastOrPresent
    private LocalDate dateTransaction;

    private LocalDateTime createdIn = LocalDateTime.now();


}
