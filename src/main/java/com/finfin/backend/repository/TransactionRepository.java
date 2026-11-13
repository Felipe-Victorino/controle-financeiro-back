package com.finfin.backend.repository;

import com.finfin.backend.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByWalletIdOrderByDateTransactionDescIdDesc(UUID walletId);

    Optional<Transaction> findByIdAndWalletId(Long id, UUID walletId);
}
