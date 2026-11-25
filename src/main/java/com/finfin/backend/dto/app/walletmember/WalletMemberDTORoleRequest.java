package com.finfin.backend.dto.app.walletmember;

import com.finfin.backend.entity.enums.RoleWallet;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WalletMemberDTORoleRequest {
    @NotNull
    private RoleWallet role;
}
