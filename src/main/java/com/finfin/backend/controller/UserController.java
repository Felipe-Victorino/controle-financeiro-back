package com.finfin.backend.controller;

import com.finfin.backend.entity.User;
import com.finfin.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class UserController {
    @Autowired
    private UserService service;

    @PostMapping("/auth/register")
    public ResponseEntity<User> register(@RequestBody @Valid User user){
        User userdb = service.insert(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(userdb);
    }
  
}
