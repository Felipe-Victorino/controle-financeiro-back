package com.finfin.backend.service;

import com.finfin.backend.entity.Transaction;
import com.finfin.backend.entity.User;
import com.finfin.backend.entity.Wallet;
import com.finfin.backend.entity.WalletMember;
import com.finfin.backend.entity.enums.RoleWallet;
import com.finfin.backend.exception.AccessDeniedException;
import com.finfin.backend.exception.ResourceNotFoundException;
import com.finfin.backend.repository.WalletMemberRepository;
import com.finfin.backend.repository.WalletRepository;

import com.finfin.backend.security.UserAuthProvider;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class WalletService {

    @Autowired
    WalletRepository repository;

    @Autowired
    UserAuthProvider authProvider;

    public Wallet findById(@NonNull UUID id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("{walletmember.notfound}"));
    }


    @Autowired
    WalletMemberRepository memberRepository;

    @Transactional
    public Wallet insert(@NonNull Wallet w) {
        User owner = authProvider.getAuthenticatedUser();
        w.setOwner(owner);
        Wallet saved = repository.save(w);

        WalletMember ownerMember = new WalletMember();
        ownerMember.setWallet(saved);
        ownerMember.setUser(owner);
        ownerMember.setRole(RoleWallet.OWNER);
        saved.getMembers().add(memberRepository.save(ownerMember)); // keeps the response mapping complete
        return saved;
    }

    public void delete(@NonNull UUID id) {
        repository.delete(findOwned(id));
    }


    public Wallet update(@NonNull Wallet w) {
        Wallet wdb = findOwned(w.getId());
        if (!wdb.getOwner().equals(authProvider.getAuthenticatedUser())) {
            throw new AccessDeniedException("Não autorizado");
        }
        wdb.setName(w.getName());
        wdb.setDescription(w.getDescription());
        return repository.save(wdb);
    }

    public Wallet findOwned(@NonNull UUID id) {
        Wallet wallet = findById(id);
        User current = authProvider.getAuthenticatedUser();
        if (!wallet.getOwner().getId().equals(current.getId())) {
            throw new AccessDeniedException("Não autorizado");
        }
        return wallet;
    }

    public Wallet findAccessible(@NonNull UUID id) {
        Wallet wallet = findById(id);
        User current = authProvider.getAuthenticatedUser();
        boolean isOwner = wallet.getOwner().getId().equals(current.getId());
        boolean isMember = wallet.getMembers().stream()
                .anyMatch(m -> m.getUser().getId().equals(current.getId()));
        if (!isOwner && !isMember) {
            throw new ResourceNotFoundException("Carteira não encontrada"); // 404 hides that it exists
        }
        return wallet;
    }


    public Wallet addTransaction(@NonNull Transaction t, @NonNull Wallet w) {
        Wallet wdb = findAccessible(w.getId());
        wdb.getTransactions().add(t);
        return repository.save(wdb);
    }

    public Wallet updateOwner(@NonNull Wallet w) {
        Wallet wbd = findOwned(w.getId());
        wbd.setOwner(w.getOwner());
        return repository.save(wbd);
    }


    public List<Wallet> listAll() {
        User user = authProvider.getAuthenticatedUser();
        return repository.findAllByOwner(user);
    }
}
