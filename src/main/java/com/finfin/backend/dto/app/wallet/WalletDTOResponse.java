package com.finfin.backend.dto.app.wallet;

import jakarta.validation.constraints.NotBlank;
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

    UUID id;


    @Size(min = 2, max = 128)
    private String name;


    private String ownerEmail;

    List<String> memberEmails;

    @Size(max = 255)
    private String description;

    @PastOrPresent
    private LocalDateTime createdIn;
}
