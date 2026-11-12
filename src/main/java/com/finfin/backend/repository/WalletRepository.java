package com.finfin.backend.repository;

import com.finfin.backend.entity.User;
import com.finfin.backend.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {

    List<Wallet> findAllByOwner(User owner);

}
