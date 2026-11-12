package com.finfin.backend.repository;

import com.finfin.backend.entity.WalletMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WalletMemberRepository extends JpaRepository<WalletMember, Long> {

    List<WalletMember> findByWallet_Id(UUID walletId);

    Optional<WalletMember> findByIdAndWallet_Id(Long id, UUID walletId);

    boolean existsByWallet_IdAndUser_Id(UUID walletId, Long userId);
}
