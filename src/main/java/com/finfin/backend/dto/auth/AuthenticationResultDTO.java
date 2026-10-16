package com.finfin.backend.dto.auth;

public record AuthenticationResultDTO(UserAuthDTO data, String refreshToken) {
}