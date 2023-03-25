package com.backend.vroomvroom.repository.user;

import com.backend.vroomvroom.entity.user.EmailAuthEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class EmailRepositoryTest {

    @Autowired
    private IEmailAuthRepository emailAuthRepository;


    /**
     * 동일한 이메일로 인증코드를 여러번 발송했을 때 마지막 건만 가져오기
     */
    @Test
    public void findTop1ByEmailOrderBySendDateDesc_테스트() throws InterruptedException {
        //given
        emailAuthRepository.save(EmailAuthEntity.builder()
                .email("test1234@naver.com")
                .authCode("ABCD12")
                .build());

        Thread.sleep(5000);

        emailAuthRepository.save(EmailAuthEntity.builder()
                .email("test1234@naver.com")
                .authCode("1234AB")
                .build());

        //when
        EmailAuthEntity findEmail = emailAuthRepository.findTop1ByEmailOrderBySendDateDesc("test1234@naver.com").orElse(null);


        //then
        assertThat(findEmail).isNotNull();
        assertThat(findEmail.getEmail()).isEqualTo("test1234@naver.com");
        assertThat(findEmail.getAuthCode()).isEqualTo("1234AB");
    }

}
