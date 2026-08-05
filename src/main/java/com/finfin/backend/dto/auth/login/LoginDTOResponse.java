package com.finfin.backend.dto.auth.login;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LoginDTOResponse {
    //private JpaToken accessToken;
    //private TokenType;
    LocalDateTime expiresIn = LocalDateTime.now().plusHours(3);
}
