package com.finfin.backend.repository;

import com.finfin.backend.entity.WalletMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletMemberRepository extends JpaRepository<WalletMember, Long> {
}
