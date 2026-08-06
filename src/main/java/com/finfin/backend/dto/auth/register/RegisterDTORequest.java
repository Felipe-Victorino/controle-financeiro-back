package com.finfin.backend.dto.auth.register;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterDTORequest {
    @NotBlank(message = "{user.name.needed}")
    @Size(min=2, max=64)
    private String name;

    @Email(message = "Email deve ser um endereço válido")
    @NotBlank(message = "{user.email.needed}")
    private String email;

    @NotBlank(message = "{user.passwd.needed}")
    @Size(min = 8)
    private String passwd;

    @NotBlank(message= "{user.passwdConfirm.needed}")
    @Size(min = 8)
    private String passwdConfirm;
}
