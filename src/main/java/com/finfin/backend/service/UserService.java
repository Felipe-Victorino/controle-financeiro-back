package com.finfin.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Email;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.finfin.backend.entity.User;
import com.finfin.backend.repository.UserRepository;
import org.thymeleaf.context.Context;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User findById(@NonNull Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("{user.notfound}"));
    }

    public User findByEmail(@Email String email) throws UsernameNotFoundException{
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public User insert(@NonNull User user) {
        //addMapping recebe uma fonte e um destino, portanto o primeiro argumento é um getter e o segundo um setter

        user.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));
        repository.save(user);

        Context context = new Context();
        context.setVariable("name", user.getName());

        emailSenderService.sendTemplatedEmail(
                user.getEmail(),
                "Novo registro no serviço FinFin",
                "successfulRegistration",
                context
        );

        return user;
    }

    public boolean confirmPassword(@NonNull String passwd, String passwdConfirm) {
        return passwd.equals(passwdConfirm);
    }


    public User update(@NonNull User user) {
        User userdb = findById(user.getId());
        userdb.setAddress(user.getAddress());
        userdb.setName(user.getName());
        userdb.setUpdatedIn(LocalDateTime.now());
        return repository.save(userdb);
    }

    public User updateEmail(@NonNull User user) {
        User userdb = findById(user.getId());
        userdb.setEmail(user.getEmail());
        userdb.setUpdatedIn(LocalDateTime.now());
        return repository.save(userdb);
    }

    public User updateHashedPassword(@NonNull User user) {
        User userdb = findById(user.getId());
        userdb.setHashedPassword(passwordEncoder.encode(user.getHashedPassword()));
        userdb.setUpdatedIn(LocalDateTime.now());
        return repository.save(userdb);
    }

    public void delete(@NonNull Long id) {
        User user = findById(id);
        repository.delete(user);
    }

    public List<User> listAll() {
        return repository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String email) throws UsernameNotFoundException {
        return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

    }
}