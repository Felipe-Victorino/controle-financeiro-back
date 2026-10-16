package com.finfin.backend.dto.auth;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserAuthDTO {
    private String name;
    private String email;
    private String token; // access token

    public void setName(@Email(message = "{user.email.needed}") String email) {
    }
}


