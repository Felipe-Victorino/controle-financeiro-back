package com.finfin.backend.service;

import com.finfin.backend.dto.app.walletmember.WalletMemberDTORequest;
import com.finfin.backend.entity.User;
import com.finfin.backend.entity.Wallet;
import com.finfin.backend.entity.WalletMember;
import com.finfin.backend.entity.enums.RoleWallet;
import com.finfin.backend.exception.ResourceNotFoundException;
import com.finfin.backend.repository.WalletMemberRepository;
import com.finfin.backend.security.UserAuthProvider;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletMemberService {

    @Autowired
    WalletMemberRepository repository;

    @Autowired
    UserAuthProvider authProvider;

    @Autowired
    WalletService walletService;

    @Autowired
    UserService userService;

    public List<WalletMember> listMembers(UUID walletId) {
        Wallet wallet = walletService.findAccessible(walletId);
        return repository.findByWallet_Id(wallet.getId());
    }

    public WalletMember addMember(UUID walletId, WalletMemberDTORequest request) {
        if (request.getRole() == RoleWallet.OWNER) {
            throw new IllegalArgumentException("Não é possível atribuir o papel de proprietário");
        }
        Wallet wallet = walletService.findOwned(walletId);

        User user = userService.findByEmail(request.getUserEmail());
        if (user == null) {
            throw new ResourceNotFoundException("Usuário não encontrado");
        }
        if (repository.existsByWallet_IdAndUser_Id(walletId, user.getId())) {
            throw new IllegalStateException("Usuário já é membro desta carteira"); // map to 409 in your handler
        }

        WalletMember member = new WalletMember();
        member.setWallet(wallet);
        member.setUser(user);
        member.setRole(request.getRole());
        return repository.save(member);
    }

    public WalletMember updateRole(UUID walletId, Long memberId, RoleWallet role) {

        walletService.findOwned(walletId);
        WalletMember member = findInWallet(walletId, memberId);
        if (member.getRole() == RoleWallet.OWNER) {
            throw new IllegalStateException("O proprietário não pode ser alterado ou removido");
        }
        member.setRole(role);
        return repository.save(member);
    }

    public void removeMember(UUID walletId, Long memberId) {
        walletService.findOwned(walletId);
        WalletMember member = findInWallet(walletId, memberId);
        if (member.getRole() == RoleWallet.OWNER) {
            throw new IllegalStateException("O proprietário não pode ser alterado ou removido");
        }
        repository.delete(member);
    }

    private WalletMember findInWallet(UUID walletId, Long memberId) {
        return repository.findByIdAndWallet_Id(memberId, walletId)
                .orElseThrow(() -> new ResourceNotFoundException("Membro não encontrado"));
    }
}
