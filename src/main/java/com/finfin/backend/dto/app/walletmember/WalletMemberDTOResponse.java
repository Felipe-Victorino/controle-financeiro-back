package com.finfin.backend.dto.app.walletmember;

import com.finfin.backend.entity.User;
import com.finfin.backend.entity.Wallet;
import com.finfin.backend.entity.enums.RoleWallet;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WalletMemberDTOResponse {

    @NotNull
    private Long id;

    @NotBlank
    private UUID walletId;

    @NotBlank
    private String userEmail;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoleWallet role;

    @PastOrPresent
    private LocalDateTime joinedIn;
}
