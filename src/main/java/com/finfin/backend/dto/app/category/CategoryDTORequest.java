package com.finfin.backend.dto.app.category;

import com.finfin.backend.entity.enums.TransactionType;
import jakarta.persistence.EnumeratedValue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CategoryDTORequest {

    Long id;

    @NotBlank
    private Long ownerId;

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
}
