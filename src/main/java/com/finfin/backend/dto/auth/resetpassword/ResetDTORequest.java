package com.finfin.backend.dto.auth.resetpassword;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

@Data
@AllArgsConstructor
public class ResetDTORequest {

    @NotBlank
    @UUID
    private String token;

    @NotBlank(message = "{user.passwd.needed}")
    @Size(min = 8)
    private String passwd;

    @NotBlank(message = "{user.passwd.needed}")
    @Size(min = 8)
    private String passwdConfirm;
}
