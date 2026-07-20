package com.finfin.backend.entity;

import com.finfin.backend.entity.enums.TransactionType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumeratedValue;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotBlank
    @JoinColumn(name = "id_user")
    private User owner;

    @NotBlank(message ="{category.name.needed}")
    @Size(max=80)
    private String name;

    private String color;

    private String icon;

    @NotBlank
    @EnumeratedValue
    @Pattern(regexp = "RECEIPT|EXPENSE")
    private TransactionType type;

    private boolean isActive;

}
