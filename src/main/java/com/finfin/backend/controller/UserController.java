package com.finfin.backend.controller;


import com.finfin.backend.dto.app.user.UserDTOPasswordRequest;
import com.finfin.backend.dto.app.user.UserDTOResponse;
import com.finfin.backend.exception.AccessDeniedException;
import com.finfin.backend.security.UserAuthProvider;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finfin.backend.dto.auth.register.RegisterDTORequest;
import com.finfin.backend.entity.User;
import com.finfin.backend.service.UserService;

import jakarta.validation.Valid;

import java.time.LocalDateTime;


@RestController
@RequestMapping("users/me")
public class UserController {

    @Autowired
    UserAuthProvider userAuthProvider;

    @Autowired
    private UserService service;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserAuthProvider authProvider;

    @GetMapping
    public ResponseEntity<UserDTOResponse> getAuthenticatedUser() {

        User userdb = userAuthProvider.getAuthenticatedUser();
        UserDTOResponse response = mapper.map(userdb, UserDTOResponse.class);
        return ResponseEntity.ok().body(response);
    }

    @PutMapping("/password")
    public ResponseEntity<Void> updatePassword(@RequestBody UserDTOPasswordRequest request) {


        User user = authProvider.getAuthenticatedUser();

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getHashedPassword())) {
            throw new AccessDeniedException("Senha atual incorreta"); // or a dedicated exception mapped to 400/403
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getHashedPassword())) {
            throw new IllegalArgumentException("A nova senha deve ser diferente da atual");
        }

        user.setHashedPassword(request.getNewPassword());
        user.setUpdatedIn(LocalDateTime.now());
        service.updateHashedPassword(user);

        return ResponseEntity.ok(null);
    }


}
