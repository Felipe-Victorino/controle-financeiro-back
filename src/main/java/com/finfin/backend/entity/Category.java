package com.finfin.backend.entity;

import com.finfin.backend.entity.enums.TransactionType;
import jakarta.persistence.Column;
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
    @Column(name = "column_id")
    private Long id;

    @ManyToOne
    @NotBlank(message = "{category.owner.needed}")
    @JoinColumn(name = "user_id")
    private User owner;

    @NotBlank(message ="{category.name.needed}")
    @Size(max=80)
    private String name;

    @Pattern(
            //traduzindo: ou um valor formatado desse jeito -> #FFF ou desse outro -> #FF00FF
            regexp = "^#([0-9a-fA-F]{3}|[0-9a-fA-F]{6})$",
            message = "Valor deve ser uma cor válida"
    )
    private String color;


    private String icon;

    @NotBlank
    @EnumeratedValue
    @Pattern(regexp = "RECEIPT|EXPENSE")
    private TransactionType type;

    private boolean isActive;

}
