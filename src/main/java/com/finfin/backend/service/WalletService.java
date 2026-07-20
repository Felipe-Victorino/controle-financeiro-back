package com.finfin.backend.service;

import com.finfin.backend.entity.Transaction;
import com.finfin.backend.entity.Wallet;
import com.finfin.backend.entity.WalletMember;
import com.finfin.backend.repository.WalletRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class WalletService{

    @Autowired
    WalletRepository repository;
    public Wallet findById(UUID id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("{walletmember.notfound}"));
    }


    public Wallet insert(Wallet w) {
        return repository.save(w);
    }

    public void delete(UUID id) {
        repository.delete(findById(id));
    }


    public Wallet update(Wallet w) {
        Wallet wdb = findById(w.getId());
        wdb.setName(w.getName());
        wdb.setDescription(w.getDescription());
        return repository.save(wdb);
    }

    public Wallet addMember(WalletMember wm, Wallet w){
        Wallet wdb = findById(w.getId());
        wdb.getMembers().add(wm);
        return repository.save(wdb);
    }

    public Wallet addTransaction(Transaction t, Wallet w){
        Wallet wdb = findById(w.getId());
        wdb.getTransactions().add(t);
        return repository.save(wdb);
    }

    public Wallet updateOwner(Wallet w){
        Wallet wbd = findById(w.getId());
        wbd.setOwner(w.getOwner());
        return repository.save(wbd);
    }


    public List<Wallet> listAll() {
        return List.of();
    }
}
