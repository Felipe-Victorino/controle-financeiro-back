package com.finfin.backend.controller;

import com.finfin.backend.dto.app.user.UserDTORequest;
import com.finfin.backend.dto.auth.AuthenticationResultDTO;
import com.finfin.backend.dto.auth.UserAuthDTO;
import com.finfin.backend.dto.auth.forgotpassword.ForgotDTORequest;
import com.finfin.backend.dto.auth.register.RegisterDTORequest;
import com.finfin.backend.dto.auth.register.RegisterDTOResponse;
import com.finfin.backend.dto.auth.resetpassword.ResetDTORequest;
import com.finfin.backend.entity.PasswordRecoveryToken;
import com.finfin.backend.entity.User;
import com.finfin.backend.exception.ResourceNotFoundException;
import com.finfin.backend.service.AuthenticationService;
import com.finfin.backend.service.PasswordRecoveryTokenService;
import com.finfin.backend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final String COOKIE_REFRESH_TOKEN = "refreshToken";

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    PasswordRecoveryTokenService passwordRecoveryTokenService;

    @Autowired
    UserService userService;

    @Value("${jwt.refresh.expiration}")
    private long refreshExpiration;

    @PostMapping("/login")
    public ResponseEntity<UserAuthDTO> login(
            @RequestBody UserDTORequest user,
            HttpServletResponse response
    ) {
        AuthenticationResultDTO result = authenticationService.authenticate(user);
        addCookieRefreshToken(response, result.refreshToken());
        return ResponseEntity.ok(result.data());
    }

    @PostMapping("/refresh")
    public ResponseEntity<UserAuthDTO> refresh(
            @CookieValue(name = COOKIE_REFRESH_TOKEN, required = false) String refreshToken,
            HttpServletResponse response) {
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        try {
            AuthenticationResultDTO resultado = authenticationService.renew(refreshToken);
            addCookieRefreshToken(response, resultado.refreshToken());
            return ResponseEntity.ok(resultado.data());
        } catch (RuntimeException ex) {
            removeCookieRefreshToken(response);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @CookieValue(name = COOKIE_REFRESH_TOKEN, required = false) String refreshToken,
            HttpServletResponse response) {
        if (refreshToken != null) {
            authenticationService.logout(refreshToken);
        }
        removeCookieRefreshToken(response);
        return ResponseEntity.noContent().build();
    }

    private void addCookieRefreshToken(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_REFRESH_TOKEN, refreshToken)
                .httpOnly(true)
                .secure(false) // true em produção, com HTTPS
                .sameSite("Lax")
                .path("/api/v1/auth")
                .maxAge(Duration.ofMillis(refreshExpiration))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private void removeCookieRefreshToken(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_REFRESH_TOKEN, "")
                .httpOnly(true).secure(false).sameSite("Lax")
                .path("/auth")
                .maxAge(0) // maxAge 0 = o navegador apaga o cookie
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }


    @PostMapping("register")
    public ResponseEntity<RegisterDTOResponse> register(@RequestBody @Valid RegisterDTORequest request) {

        User userdb = userService.insert(this.mapper.map(request, User.class));

        RegisterDTOResponse response = new RegisterDTOResponse(
                userdb.getId(),
                userdb.getName(),
                userdb.getEmail(),
                userdb.getCreatedIn()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody @Valid ForgotDTORequest request) {

        User user = userService.findByEmail(request.getEmail());
        if (user == null) {
            throw new ResourceNotFoundException("Email não encontrado");
        }
        PasswordRecoveryToken prt = passwordRecoveryTokenService.createNew(user);

        String response = "Token irá expirar em uma hora";

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetDTORequest request) {

        PasswordRecoveryToken pwt = passwordRecoveryTokenService.findByToken(UUID.fromString(request.getToken()));

        if (LocalDateTime.now().isAfter(pwt.getExpirationTime())) {
            String response = "Token expirado";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        User user = pwt.getUser();
        if (user == null) {
            String response = "Token inválido";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        }

        if (pwt.isUsed()) {

            String response = "Token já utilizado";
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);

        } else if (userService.confirmPassword(request.getNewPasswd(), request.getConfirmPasswd())) {

            user.setHashedPassword(request.getNewPasswd());
            pwt.setUsed(true);
            userService.updateHashedPassword(user);

            String response = "Senha modificada com sucesso";
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        throw new RuntimeException("Erro desconhecido");
    }

}