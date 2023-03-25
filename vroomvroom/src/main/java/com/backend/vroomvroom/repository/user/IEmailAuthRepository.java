package com.backend.vroomvroom.repository.user;

import com.backend.vroomvroom.entity.user.EmailAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IEmailAuthRepository extends JpaRepository<EmailAuthEntity, Long> {
    Optional<EmailAuthEntity> findTop1ByEmailOrderBySendDateDesc(String email);
}
