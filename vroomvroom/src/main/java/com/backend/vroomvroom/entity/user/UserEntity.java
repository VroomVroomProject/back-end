package com.backend.vroomvroom.entity.user;

import com.backend.vroomvroom.entity.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    private Long money;

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();

    @Column(name = "refresh_token")
    private String refreshToken;

    public UserEntity(Long id, String loginId, String password, String email, String nickname, Long money, List<Role> roles, String refreshToken) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.money = 500000L; //회원가입 시 기본 돈을 50만원으로 세팅한다.
        this.roles = Collections.singletonList(Role.ROLE_USER); //회원가입 시 기본 권한을 ROLE_USER로 세팅한다.
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
