package com.finfin.backend.service;

import com.finfin.backend.entity.WalletMember;
import com.finfin.backend.repository.WalletMemberRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletMemberService {

    @Autowired
    WalletMemberRepository repository;

    public WalletMember findById(Long id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("{walletmember.notfound}"));
    }


    public WalletMember insert(@NonNull WalletMember wm) {
        return repository.save(wm);
    }


    public void delete(@NonNull Long id) {
        repository.delete(findById(id));
    }

    public WalletMember update(@NonNull WalletMember wm) {
        WalletMember wmdb = findById(wm.getId());
        wmdb.setRole(wm.getRole());
        wmdb.setWallet(wmdb.getWallet());
        wmdb.setUser(wm.getUser());
        return repository.save(wmdb);
    }

    public List<WalletMember> listAll() {
        return repository.findAll();
    }
}
