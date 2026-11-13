package com.finfin.backend.dto.app.transaction;

import com.finfin.backend.entity.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTORequest {
    @NotNull
    private Long categoryId;
    @NotNull
    private TransactionType type;
    @NotNull
    @DecimalMin("0.01")
    @Digits(integer = 13, fraction = 2)
    private BigDecimal value;
    @Size(max = 255)
    private String description;
    @NotNull
    private LocalDate dateTransaction;


}
