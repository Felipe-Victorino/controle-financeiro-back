package com.finfin.backend.dto.auth.login;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class LoginDTOResponse {
    //private JpaToken accessToken
    //private TokenType;
    LocalDateTime expiresIn;
}
