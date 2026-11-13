package com.finfin.backend.dto.app.transaction;

import com.finfin.backend.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTOResponse {

    private Long id;
    private UUID walletId;
    private Long categoryId;
    private TransactionType type;
    private BigDecimal value;
    private String description;
    private LocalDate dateTransaction;
    private String createdBy;       // the creator's name
    private LocalDateTime createdIn;
}
