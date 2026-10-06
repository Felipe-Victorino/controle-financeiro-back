package com.finfin.backend.dto.app.wallet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WalletDTORequest {

    @NotBlank
    @Size(min = 2, max = 128)
    private String name;

    @NotBlank
    private String owner;

    @Size(max = 255)
    private String description;

    @NotBlank
    @PastOrPresent
    private LocalDateTime createdIn;

}
