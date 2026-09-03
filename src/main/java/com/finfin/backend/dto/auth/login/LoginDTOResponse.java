package com.finfin.backend.dto.auth.login;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginDTOResponse {
    //private JpaToken accessToken;
    //private TokenType;

    @NotBlank
    String name;

    @Future
    LocalDateTime expiresIn = LocalDateTime.now().plusHours(3);
}
