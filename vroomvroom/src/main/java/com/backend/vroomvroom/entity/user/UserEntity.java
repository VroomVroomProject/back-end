package com.backend.vroomvroom.entity.user;

import com.backend.vroomvroom.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Entity
@Table(name = "tm_user")
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column(name = "login_id")
    private String loginId;

    private String password;

    private String email;

    private String nickname;

    @Column(name = "refresh_token")
    private String refreshToken;

    public UserEntity(Long id, String nickname, String loginId, String password, String email) {
        this.id = id;
        this.nickname = nickname;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
    }


}
