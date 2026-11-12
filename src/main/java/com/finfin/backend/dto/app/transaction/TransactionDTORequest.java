package com.finfin.backend.dto.app.transaction;

import com.finfin.backend.entity.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTORequest {

    @NotNull
    UUID walletID;

    @NotNull
    Long categoryID;

    @NotNull
    @Enumerated(EnumType.STRING)
    TransactionType type;

    @NotNull
    BigDecimal value;

    @Size(max = 255)
    String description;


}
