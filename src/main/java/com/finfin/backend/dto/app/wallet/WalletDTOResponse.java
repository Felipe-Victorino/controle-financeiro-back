package com.finfin.backend.dto.app.wallet;

import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTOResponse {

    private UUID id;
    private String name;
    private String ownerEmail;
    private List<String> memberEmails;
    private String description;
    private LocalDateTime createdIn;
}
