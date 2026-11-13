package com.finfin.backend.dto.app.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTOPasswordRequest {
    @Size(min = 8)
    @NotBlank(message = "{user.passwd.needed}")
    private String currentPassword;
    @Size(min = 8)
    @NotBlank(message = "{user.passwd.needed}")
    private String newPassword;
}
