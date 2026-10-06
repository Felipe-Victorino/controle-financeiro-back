package com.finfin.backend.dto.app.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTOResponse {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    private String address;

    @NotBlank
    private LocalDateTime createdIn;
}
