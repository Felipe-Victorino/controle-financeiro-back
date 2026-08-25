package com.finfin.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Email;
import org.jspecify.annotations.NonNull;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finfin.backend.dto.auth.register.RegisterDTORequest;
import com.finfin.backend.entity.User;
import com.finfin.backend.repository.UserRepository;
import org.thymeleaf.context.Context;

@Service
public class UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private EmailSenderService emailSenderService;

    @Autowired
    private ModelMapper mapper;


    public User findById(Long id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("{user.notfound}"));
    }

    public User findByEmail(@Email String email){
        return repository.findByEmail(email);
    }

    public User insert (@NonNull RegisterDTORequest userRequest){
        TypeMap<RegisterDTORequest, User> propertyMapper = this.mapper.createTypeMap(RegisterDTORequest.class, User.class);

        //addMapping recebe uma fonte e um destino, portanto o primeiro argumento é um getter e o segundo um setter

        propertyMapper.addMapping(RegisterDTORequest::getPasswd, User::setHashedPassword);
        User user = this.mapper.map(userRequest, User.class);

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

    public boolean confirmPassword(@NonNull String passwd, String passwdConfirm){
        return passwd.equals(passwdConfirm);
    }


    public User update(@NonNull User user){
        User userdb = findById(user.getId());
        userdb.setAddress(user.getAddress());
        userdb.setName(user.getName());
        userdb.setUpdatedIn(LocalDateTime.now());
        return repository.save(userdb);
    }

    public User updateEmail(@NonNull User user){
        User userdb = findById(user.getId());
        userdb.setEmail(user.getEmail());
        userdb.setUpdatedIn(LocalDateTime.now());
        return repository.save(userdb);
    }

    public User updateHashedPassword(@NonNull User user){
        User userdb = findById(user.getId());
        userdb.setHashedPassword(user.getHashedPassword());
        userdb.setUpdatedIn(LocalDateTime.now());
        return repository.save(userdb);
    }

    public void delete(@NonNull Long id) {
        User user = findById(id);
        repository.delete(user);
    }

    public List<User> listAll (){
        return repository.findAll();
    }
}
