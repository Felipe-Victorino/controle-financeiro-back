package com.finfin.backend.dto.auth.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginDTORequest {
    @Email(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String passwd;
}
