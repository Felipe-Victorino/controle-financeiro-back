package com.finfin.backend.controller;


import com.finfin.backend.dto.UserDTOResponse;
import org.modelmapper.ModelMapper;
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
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    
    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<UserDTOResponse> register(@RequestBody @Valid RegisterDTORequest request){
        User userdb = service.insert(this.mapper.map(request, User.class));

        return ResponseEntity.status(HttpStatus.CREATED).body(this.mapper.map(userdb, UserDTOResponse.class));
    }

  
}
