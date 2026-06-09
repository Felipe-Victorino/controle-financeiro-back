package com.finfin.backend.entity;

import com.finfin.backend.entity.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumeratedValue;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import jakarta.validation.constraints.Positive;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotBlank
    private Wallet wallet;

    @ManyToOne
    private Category category;

    @ManyToOne
    private User createdBy;

    @EnumeratedValue
    private TransactionType type;

    @NotBlank
    @Positive
    private BigDecimal value;

    private String description;

    private LocalDate dateTransaction;

    private LocalDateTime createdIn = LocalDateTime.now();


}
