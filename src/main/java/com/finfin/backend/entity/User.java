package com.finfin.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min=2, max=100)
    private String name;

    @Email(message = "Email is required")
    private String email;

    @NotBlank
    private String hashedPassword;

    @PastOrPresent
    private LocalDateTime createdIn = LocalDateTime.now();

    @PastOrPresent
    private LocalDateTime updatedIn;


}
