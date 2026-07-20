package com.finfin.backend.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.finfin.backend.dto.auth.register.RegisterDTORequest;
import com.finfin.backend.entity.User;
import com.finfin.backend.repository.UserRepository;

@Service
public class UserService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private ModelMapper modelMapper;


    public User findById(Long id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("{user.notfound}"));
    }


    public User insert (RegisterDTORequest user){
        TypeMap<RegisterDTORequest, User> propertyMapper = this.modelMapper.createTypeMap(RegisterDTORequest.class, User.class);
        //addMapping recebe uma fonte e um destino, portanto o primeiro argumento é um getter e o segundo um setter
        propertyMapper.addMapping(RegisterDTORequest::getPasswd, User::setHashedPassword);
        return repository.save(this.modelMapper.map(user, User.class));
    }


    public User update(User user){
        User userdb = findById(user.getId());
        // TODO: address

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
