package com.finfin.backend.dto.app.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTORequest {
    @NotBlank(message = "{user.email.needed}")
    @Email
    private String email;

    @NotBlank(message = "{user.passwd.needed}")
    @Size(min = 8)
    private String passwd;
}
