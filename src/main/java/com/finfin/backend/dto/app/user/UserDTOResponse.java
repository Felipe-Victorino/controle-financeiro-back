package com.finfin.backend.dto.app.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDTOResponse {

    @NotBlank(message = "{user.name.needed}")
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Email(message = "{user.email.needed}")
    private String email;

    private List<String> authorities;

    @PastOrPresent
    private LocalDateTime createdIn;

    @PastOrPresent
    private LocalDateTime updatedIn;
}
