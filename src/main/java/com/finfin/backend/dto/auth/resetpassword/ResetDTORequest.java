package com.finfin.backend.dto.auth.resetpassword;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResetDTORequest {

    @NotBlank
    private String token;

    @NotBlank
    private String passwd;

    @NotBlank
    private String newPasswd;
}
