package com.finfin.backend.dto.auth.forgotpassword;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForgotDTOResponse {

    @NotBlank
    private String message;

    @NotBlank
    private String token;
}
