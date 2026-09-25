package com.finfin.backend.dto.app;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTOResponse {

    private Long id;

    @NotBlank(message = "{user.name.needed}")
    @Size(min=2, max=100)
    private String name;

    @NotBlank
    @Email(message = "{user.email.needed}")
    private String email;

    @PastOrPresent
    private LocalDateTime createdIn;

    @PastOrPresent
    private LocalDateTime updatedIn;
}
