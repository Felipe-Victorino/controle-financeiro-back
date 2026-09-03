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


    private Long id;

    @NotBlank(message = "{user.name.needed}")
    @Size(min=2, max=100)
    private String name;

    @NotBlank
    @Email(message = "{user.email.needed}")
    private String email;

    @NotBlank
    @FutureOrPresent
    private LocalDateTime createdIn;
}
