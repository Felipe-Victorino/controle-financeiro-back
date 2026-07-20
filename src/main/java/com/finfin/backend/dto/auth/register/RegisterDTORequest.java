package com.finfin.backend.dto.auth.register;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTORequest {
    @NotBlank(message = "Name is required")
    @Size(min=2, max=100)
    private String name;

    @Email(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String passwd;

    @NotBlank(message= "Password confirmation is required")
    private String passwdConfirm;
}
