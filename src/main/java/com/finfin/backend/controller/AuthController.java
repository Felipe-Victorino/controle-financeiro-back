package com.finfin.backend.controller;

import com.finfin.backend.dto.auth.forgotpassword.ForgotDTORequest;
import com.finfin.backend.dto.auth.forgotpassword.ForgotDTOResponse;
import com.finfin.backend.dto.auth.login.LoginDTORequest;
import com.finfin.backend.dto.auth.login.LoginDTOResponse;
import com.finfin.backend.dto.auth.register.RegisterDTORequest;
import com.finfin.backend.dto.auth.register.RegisterDTOResponse;
import com.finfin.backend.dto.auth.resetpassword.ResetDTORequest;
import com.finfin.backend.dto.auth.resetpassword.ResetDTOResponse;
import com.finfin.backend.entity.PasswordRecoveryToken;
import com.finfin.backend.entity.User;
import com.finfin.backend.exception.ResourceNotFoundException;
import com.finfin.backend.service.PasswordRecoveryTokenService;
import com.finfin.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    PasswordRecoveryTokenService passwordRecoveryTokenService;

    @GetMapping()
    public String defaultMessage(){return "Authentication";}

    @PostMapping("register")
    public ResponseEntity<RegisterDTOResponse> register(@RequestBody @Valid RegisterDTORequest user){

        User userdb = userService.insert(user);

        RegisterDTOResponse response = new RegisterDTOResponse(
                userdb.getId(),
                userdb.getName(),
                userdb.getEmail(),
                userdb.getCreatedIn()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("forgot-password")
    public ResponseEntity<ForgotDTOResponse> forgotPassword(@RequestBody @Valid ForgotDTORequest request){

        User user = userService.findByEmail(request.getEmail());
        if(user == null) {
            throw new ResourceNotFoundException("Email não encontrado");
        }
        PasswordRecoveryToken prt = passwordRecoveryTokenService.createNew(user);

        ForgotDTOResponse response = new ForgotDTOResponse(
                "Token irá expirar em uma hora",
                prt.getToken()
        );

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("reset-password")
    public ResponseEntity<ResetDTOResponse> resetPassword(@RequestBody @Valid ResetDTORequest request){

        PasswordRecoveryToken pwt = passwordRecoveryTokenService.findByToken(UUID.fromString(request.getToken()));

        if (LocalDateTime.now().isAfter(pwt.getExpirationTime())){
            ResetDTOResponse response = new ResetDTOResponse("Token expirado");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        User user = pwt.getUser();
        if(user == null){
            ResetDTOResponse response = new ResetDTOResponse("Token inválido");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        if(pwt.isUsed()){

            ResetDTOResponse response = new ResetDTOResponse("Token já utilizado");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

        } else if(userService.confirmPassword(request.getNewPasswd(), request.getConfirmPasswd())){

            user.setHashedPassword(request.getNewPasswd());
            pwt.setUsed(true);
            userService.updateHashedPassword(user);

            ResetDTOResponse response = new ResetDTOResponse("Senha modificada com sucesso");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        throw new RuntimeException("Erro desconhecido");
    }

    @PostMapping("login")
    public ResponseEntity<LoginDTOResponse> login(@RequestBody @Valid LoginDTORequest request){
        User user = userService.findByEmail(request.getEmail());
        if(user == null){
            throw new ResourceNotFoundException("Usuário não encontrado");
        }

        LoginDTOResponse response = new LoginDTOResponse();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }



}
