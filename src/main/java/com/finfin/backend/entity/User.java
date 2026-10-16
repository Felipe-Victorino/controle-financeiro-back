package com.finfin.backend.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank(message = "{user.name.needed}")
    @Size(min=2, max=100)
    private String name;

    @Email(message = "{user.email.needed}")
    @Column(unique = true)
    private String email;

    @Size(min = 0, max = 256)
    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,
            orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<UserProfile> userProfiles = new HashSet<>();

    @NotBlank(message = "{user.passwd.needed}")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String hashedPassword;

    @PastOrPresent
    private LocalDateTime createdIn = LocalDateTime.now();

    @PastOrPresent
    private LocalDateTime updatedIn;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (userProfiles == null) return List.of();
        return userProfiles.stream()
                .map(up -> new SimpleGrantedAuthority(up.getProfile().getDescription()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() { return hashedPassword; }

    @Override
    public String getUsername() { return email; }

}
