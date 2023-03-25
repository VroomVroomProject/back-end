package com.backend.vroomvroom.repository.user;

import com.backend.vroomvroom.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByLoginId(String loginId);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByNickname(String nickName);

}
