package com.finfin.backend.dto.auth.register;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class RegisterDTOResponse {

    private long id;

    @NotBlank(message = "Name is required")
    @Size(min=2, max=100)
    private String name;

    @Email(message = "Email is required")
    private String email;

    @FutureOrPresent
    private LocalDateTime createdAt;
}
