package com.finfin.backend.service;

import com.finfin.backend.entity.User;
import com.finfin.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService implements BackendService<User, Long>{

    @Autowired
    private UserRepository repository;

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(()->new RuntimeException("Usuario not found"));
    }

    @Override
    public User insert (User user){
        return repository.save(user);
    }

    @Override
    public User update(User user){
        User userdb = findById(user.getId());

        userdb.setEmail(user.getEmail());
        userdb.setHashedPassword(user.getHashedPassword());
        userdb.setName(user.getName());
        userdb.setUpdatedIn(LocalDateTime.now());
        return null;
    }

    @Override
    public void delete(Long id) {
        User user = findById(id);
        repository.delete(user);


    }

    @Override
    public List<User> listAll (){
        return repository.findAll();
    }
}
