package com.finfin.backend.service;

import com.finfin.backend.entity.PasswordRecoveryToken;
import com.finfin.backend.repository.PasswordRecoveryTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PasswordRecoveryTokenService{
    @Autowired
    PasswordRecoveryTokenRepository repository;

    public PasswordRecoveryToken findById(Long id) {
        return repository.findById(id).orElseThrow(()-> new RuntimeException("{rectoken.notfound}"));
    }

    public PasswordRecoveryToken insert(PasswordRecoveryToken passwordRecoveryToken) {
        return repository.save(passwordRecoveryToken);
    }


    public void delete(Long id) {
        repository.delete(findById(id));
    }


    public PasswordRecoveryToken update(PasswordRecoveryToken passwordRecoveryToken) {
        PasswordRecoveryToken prtdb = findById(passwordRecoveryToken.getId());
        prtdb.setToken(passwordRecoveryToken.getToken());
        prtdb.setUser(passwordRecoveryToken.getUser());
        prtdb.setExpirationTime(passwordRecoveryToken.getExpirationTime());
        prtdb.setUsed(passwordRecoveryToken.isUsed());

        return repository.save(prtdb);
    }

    public PasswordRecoveryToken updateUser(PasswordRecoveryToken prt){
        PasswordRecoveryToken prtdb = findById(prt.getId());
        prtdb.setToken(prt.getToken());
        return repository.save(prtdb);
    }

    public PasswordRecoveryToken updateToken(PasswordRecoveryToken prt){
        PasswordRecoveryToken prtdb = findById(prt.getId());
        prtdb.setToken(prt.getToken());
        return repository.save(prtdb);
    }

    public PasswordRecoveryToken updateExpirationTime(PasswordRecoveryToken prt){
        PasswordRecoveryToken prtdb = findById(prt.getId());
        prtdb.setExpirationTime(prt.getExpirationTime());
        return repository.save(prtdb);
    }

    public PasswordRecoveryToken updateUsed(PasswordRecoveryToken prt){
        PasswordRecoveryToken prtdb = findById((prt.getId()));
        prtdb.setUsed(prt.isUsed());
        return repository.save(prtdb);
    }


    public List<PasswordRecoveryToken> listAll() {
        return repository.findAll();
    }
}
