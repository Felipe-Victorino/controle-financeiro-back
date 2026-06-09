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

@Data
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotBlank
    private User user;

    @NotBlank
    private String name;

    private String color;

    private String icon;

    @NotBlank
    @EnumeratedValue
    private TransactionType type;

    private boolean isActive;

}
