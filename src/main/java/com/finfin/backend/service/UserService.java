package com.finfin.backend.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.finfin.backend.dto.auth.forgotpassword.ForgotDTORequest;
import jakarta.validation.constraints.Email;
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
    private ModelMapper modelMapper;


    public User findById(Long id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("{user.notfound}"));
    }

    public User findByEmail(@Email String email){
        return repository.findByEmail(email);
    }

    public User insert (RegisterDTORequest userRequest){
        TypeMap<RegisterDTORequest, User> propertyMapper = this.modelMapper.createTypeMap(RegisterDTORequest.class, User.class);

        //addMapping recebe uma fonte e um destino, portanto o primeiro argumento é um getter e o segundo um setter

        propertyMapper.addMapping(RegisterDTORequest::getPasswd, User::setHashedPassword);
        User user = this.modelMapper.map(userRequest, User.class);

        repository.save(user);

//        Context context = new Context();
//        context.setVariable("name", user.getName());
//
//        emailSenderService.sendTemplatedEmail(
//                user.getEmail(),
//                "Novo registro no serviço FinFin",
//                "successfulRegistration",
//                context
//        );

        return user;
    }

    public boolean confirmPassword(String passwd, String passwdConfirm){
        return passwd.equals(passwdConfirm);
    }



    public User update(User user){
        User userdb = findById(user.getId());
        userdb.setAddress(user.getAddress());
        userdb.setName(user.getName());
        userdb.setUpdatedIn(LocalDateTime.now());
        return repository.save(userdb);
    }

    public User updateEmail(User user){
        User userdb = findById(user.getId());
        userdb.setEmail(user.getEmail());
        userdb.setUpdatedIn(LocalDateTime.now());
        return repository.save(userdb);
    }

    public User updateHashedPassword(User user){
        User userdb = findById(user.getId());
        userdb.setHashedPassword(user.getHashedPassword());
        userdb.setUpdatedIn(LocalDateTime.now());
        return repository.save(userdb);
    }

    public void delete(Long id) {
        User user = findById(id);
        repository.delete(user);
    }

    public List<User> listAll (){
        return repository.findAll();
    }
}
