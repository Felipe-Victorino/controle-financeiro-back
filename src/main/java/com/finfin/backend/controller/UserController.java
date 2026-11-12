package com.finfin.backend.controller;


import com.finfin.backend.dto.app.user.UserDTOResponse;
import com.finfin.backend.security.UserAuthProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
@RequestMapping("/me")
public class UserController {

    @Autowired
    UserAuthProvider userAuthProvider;

    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<UserDTOResponse> getAuthenticatedUser() {

        User userdb = userAuthProvider.getAuthenticatedUser();
        UserDTOResponse response = mapper.map(userdb, UserDTOResponse.class);
        return ResponseEntity.ok().body(response);
    }


}
