package com.finfin.backend.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAuthDTO {

    private String name;

    @Email
    private String email;

    private String token; // access token

    @Future
    LocalDateTime expiresIn = LocalDateTime.now().plusHours(3);

}


