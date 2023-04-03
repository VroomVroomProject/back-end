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

    @ElementCollection(fetch = FetchType.LAZY)
    @Enumerated(EnumType.STRING)
    private List<Role> roles = new ArrayList<>();

    @Column(name = "refresh_token")
    private String refreshToken;

    public UserEntity(Long id, String loginId, String password, String email, String nickname, List<Role> roles, String refreshToken) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickname = nickname;
        this.roles = Collections.singletonList(Role.ROLE_USER);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
