package com.finfin.backend.repository;

import com.finfin.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM user u WHERE u.email = ?1")
    public User findByEmail(String email);
}
