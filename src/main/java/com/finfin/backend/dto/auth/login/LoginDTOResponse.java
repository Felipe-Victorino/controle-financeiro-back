package com.finfin.backend.dto.auth.login;

import jakarta.validation.constraints.Future;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginDTOResponse {
    //private JpaToken accessToken;
    //private TokenType;
    @Future
    LocalDateTime expiresIn = LocalDateTime.now().plusHours(3);
}
