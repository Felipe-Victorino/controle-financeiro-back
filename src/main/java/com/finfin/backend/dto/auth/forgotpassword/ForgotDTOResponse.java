package com.finfin.backend.dto.auth.forgotpassword;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ForgotDTOResponse {

    @NotBlank
    private String message;

    @NotBlank
    private UUID token;
}
