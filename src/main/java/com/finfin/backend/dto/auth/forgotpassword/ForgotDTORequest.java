package com.finfin.backend.dto.auth.forgotpassword;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ForgotDTORequest {

    @NotBlank
    private String email;
}
