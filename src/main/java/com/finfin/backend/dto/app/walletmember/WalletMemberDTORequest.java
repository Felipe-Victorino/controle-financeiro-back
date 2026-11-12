package com.finfin.backend.dto.app.walletmember;

import com.finfin.backend.entity.enums.RoleWallet;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletMemberDTORequest {

    @NotBlank
    private String userEmail;

    @Enumerated(EnumType.STRING)
    private RoleWallet role;
}
