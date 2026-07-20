package com.finfin.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{user.name.needed}")
    @Size(min=2, max=100)
    private String name;

    @Email(message = "{user.email.needed}")
    private String email;

    
    private String address;

    @NotBlank(message = "{user.passwd.needed}")
    private String hashedPassword;

    @PastOrPresent
    private LocalDateTime createdIn = LocalDateTime.now();

    @PastOrPresent
    private LocalDateTime updatedIn;


}
