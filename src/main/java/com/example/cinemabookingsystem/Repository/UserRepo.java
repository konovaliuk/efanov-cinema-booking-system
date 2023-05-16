package com.example.cinemabookingsystem.Repository;

import com.example.cinemabookingsystem.Model.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    @Modifying
    @Transactional
    @Query("update User u set u.is_admin = true where u.id = :id")
    void makeUserAdmin(@Param("id") Long id);
    Optional<User> getUserByEmail(String email);
}

