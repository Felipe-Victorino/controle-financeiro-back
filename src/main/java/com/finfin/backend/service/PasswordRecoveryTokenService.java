package com.finfin.backend.service;

import com.finfin.backend.entity.PasswordRecoveryToken;
import com.finfin.backend.entity.User;
import com.finfin.backend.repository.PasswordRecoveryTokenRepository;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.UUID;

@Service
public class PasswordRecoveryTokenService{
    @Autowired
    PasswordRecoveryTokenRepository repository;

    @Autowired
    EmailSenderService emailService;

    public PasswordRecoveryToken findById(Long id) {
        return repository.findById(id).orElseThrow(()-> new RuntimeException("{rectoken.notfound}"));
    }

    public PasswordRecoveryToken findByToken(@NonNull UUID token){
        return repository.findByToken(token);
    }

    public PasswordRecoveryToken insert(@NonNull PasswordRecoveryToken passwordRecoveryToken) {
        return repository.save(passwordRecoveryToken);
    }

    public PasswordRecoveryToken createNew(@NonNull User user){
        PasswordRecoveryToken prt = new PasswordRecoveryToken();
        prt.setUser(user);

        insert(prt);
        System.out.println(prt.getToken());

        Context context = new Context();
        context.setVariable("token", prt.getToken().toString());
        context.setVariable("user", user.getName());

        emailService.sendTemplatedEmail(
                user.getEmail(),
                "Recuperação de Senha",
                "recoveryCode",
                context
        );

        return prt;
    }


    public void delete(Long id) {
        repository.delete(findById(id));
    }


    public PasswordRecoveryToken update(@NonNull PasswordRecoveryToken passwordRecoveryToken) {
        PasswordRecoveryToken prtdb = findById(passwordRecoveryToken.getId());
        prtdb.setToken(passwordRecoveryToken.getToken());
        prtdb.setUser(passwordRecoveryToken.getUser());
        prtdb.setExpirationTime(passwordRecoveryToken.getExpirationTime());
        prtdb.setUsed(passwordRecoveryToken.isUsed());

        return repository.save(prtdb);
    }

    public PasswordRecoveryToken updateUser(@NonNull PasswordRecoveryToken prt){
        PasswordRecoveryToken prtdb = findById(prt.getId());
        prtdb.setToken(prt.getToken());
        return repository.save(prtdb);
    }

    public PasswordRecoveryToken updateToken(@NonNull PasswordRecoveryToken prt){
        PasswordRecoveryToken prtdb = findById(prt.getId());
        prtdb.setToken(prt.getToken());
        return repository.save(prtdb);
    }

    public PasswordRecoveryToken updateExpirationTime(@NonNull PasswordRecoveryToken prt){
        PasswordRecoveryToken prtdb = findById(prt.getId());
        prtdb.setExpirationTime(prt.getExpirationTime());
        return repository.save(prtdb);
    }

    public PasswordRecoveryToken updateUsed(@NonNull PasswordRecoveryToken prt){
        PasswordRecoveryToken prtdb = findById((prt.getId()));
        prtdb.setUsed(prt.isUsed());
        return repository.save(prtdb);
    }


    public List<PasswordRecoveryToken> listAll() {
        return repository.findAll();
    }
}
