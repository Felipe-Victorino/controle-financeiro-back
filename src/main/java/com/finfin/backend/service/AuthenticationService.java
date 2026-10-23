package com.finfin.backend.service;

import com.finfin.backend.dto.app.user.UserRequestDTO;
import com.finfin.backend.dto.auth.AuthenticationResultDTO;
import com.finfin.backend.dto.auth.UserAuthDTO;
import com.finfin.backend.entity.RefreshToken;
import com.finfin.backend.entity.User;
import com.finfin.backend.exception.ResourceNotFoundException;
import com.finfin.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;
    @Autowired
    private UserRepository userRepository;

    public AuthenticationResultDTO authenticate(UserRequestDTO user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPasswd()));
        Optional<User> userDb = userRepository.findByEmail(user.getEmail());
        if(userDb.isEmpty()){
            throw new ResourceNotFoundException("Usuário não encontrado");
        }

        User userV = userDb.get();
        RefreshToken refreshToken = refreshTokenService.create(userV);
        return buildResult(userV, authentication.getName(), refreshToken);
    }

    public AuthenticationResultDTO renew(String refreshTokenRecebido) {
        RefreshToken valid = refreshTokenService.validate(refreshTokenRecebido);
        User userDb = valid.getUser();
        RefreshToken novo = refreshTokenService.rotate(valid);
        return buildResult(userDb, userDb.getUsername(), novo);
    }

    public void logout(String refreshTokenRecebido) {
        refreshTokenService.revoke(refreshTokenRecebido);
    }

    private AuthenticationResultDTO buildResult(User u, String email, RefreshToken rt) {
        UserAuthDTO dto = new UserAuthDTO();
        dto.setEmail(u.getEmail());
        dto.setName(u.getName());
        dto.setToken(jwtService.generateToken(email));
        return new AuthenticationResultDTO(dto, rt.getToken());
    }
}