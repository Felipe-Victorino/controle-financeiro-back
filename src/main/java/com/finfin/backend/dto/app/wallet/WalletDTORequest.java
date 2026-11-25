package com.finfin.backend.dto.app.wallet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletDTORequest {

    @NotBlank
    @Size(min = 2, max = 128)
    private String name;

    @Size(max = 255)
    private String description;


}
