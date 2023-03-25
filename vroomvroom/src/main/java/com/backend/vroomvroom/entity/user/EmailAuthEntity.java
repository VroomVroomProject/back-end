package com.backend.vroomvroom.entity.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "tm_email_auth")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuthEntity{

    private static final Long EXPIRE_TIME = 5L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(name = "auth_code")
    private String authCode;

    @Column(name = "send_date")
    private LocalDateTime sendDate;

    @Builder
    public EmailAuthEntity(Long id, String email, String authCode) {
        this.id = id;
        this.email = email;
        this.authCode = authCode;
        this.sendDate = LocalDateTime.now();
    }


    /**
     * 인증코드 만료 여부 체크
     * @return 만료(true), 아니라면(false)
     */
    public boolean expireCheck() {
        Long minutes = Duration.between(this.sendDate.toLocalTime(), LocalDateTime.now().toLocalTime()).toMinutes();

        return minutes >= EXPIRE_TIME ? true : false;
    }
}
