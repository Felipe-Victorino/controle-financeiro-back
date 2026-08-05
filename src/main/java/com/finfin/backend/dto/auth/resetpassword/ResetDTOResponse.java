package com.finfin.backend.dto.auth.resetpassword;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResetDTOResponse {

    @NotBlank
    private String message;
}
