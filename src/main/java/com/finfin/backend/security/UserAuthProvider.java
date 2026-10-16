package com.finfin.backend.security;

import com.finfin.backend.entity.User;
import com.finfin.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.Objects;

@Component
public class UserAuthProvider {
    @Autowired
    private UserRepository userRepository;

    public User getAuthenticatedUser() {
        Object principal = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getPrincipal();
        String username;
        if ((principal instanceof UserDetails ud)) {
            username = ud.getUsername();
        } else {
            assert principal != null;
            username = principal.toString();
        }
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new NoSuchElementException("Usuário autenticado não encontrado"));
    }
}
