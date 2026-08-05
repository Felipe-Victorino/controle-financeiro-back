package com.finfin.backend.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finfin.backend.dto.auth.register.RegisterDTORequest;
import com.finfin.backend.entity.User;
import com.finfin.backend.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {
    
    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity<User> register(@RequestBody @Valid RegisterDTORequest user){
        User userdb = service.insert(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userdb);
    }

    @GetMapping()
    public String defaultMessage(){return "User Controller";}
    
  
}
