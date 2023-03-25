package com.backend.vroomvroom.service.user;

import com.backend.vroomvroom.common.exception.CommonException;
import com.backend.vroomvroom.entity.user.UserEntity;
import com.backend.vroomvroom.repository.user.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class EmailServiceTest {

    @Autowired
    private IEmailService emailService;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    private String email = "test@naver.com"; //수신받을 이메일


    /**
     * 해당 이메일로 이미 가입한 회원이 존재하는 경우 예외를 반환
     */
    @Test
    public void authenticationEmail_이메일_전송_실패() {
        //given
        userRepository.save(UserEntity.builder()
                .loginId("test1234")
                .password(passwordEncoder.encode("test1234"))
                .email(email)
                .nickname("닉네임1")
                .useYn("Y")
                .build());

        //when
        assertThatThrownBy(() ->
            emailService.authenticationEmail(email)
        ).isInstanceOf(CommonException.class);
    }

}
