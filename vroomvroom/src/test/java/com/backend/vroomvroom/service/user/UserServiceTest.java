package com.backend.vroomvroom.service.user;

import com.backend.vroomvroom.common.exception.CommonException;
import com.backend.vroomvroom.dto.user.request.SignUpRequestDto;
import com.backend.vroomvroom.dto.user.response.SignUpResponseDto;
import com.backend.vroomvroom.entity.user.EmailAuthEntity;
import com.backend.vroomvroom.repository.user.IEmailAuthRepository;
import com.backend.vroomvroom.repository.user.IUserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    IUserService userService;

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IEmailAuthRepository emailAuthRepository;

    @Test
    @Rollback(value = false)
    public void registerUser_회원가입_성공() {
        //given
        emailAuthRepository.save(EmailAuthEntity.builder()
                .email("test1234@naver.com")
                .authCode("ABCD12")
                .build());

        SignUpRequestDto signUpRequest = new SignUpRequestDto();
        signUpRequest.setLoginId("test1234");
        signUpRequest.setPassword("qwer1234!");
        signUpRequest.setAuthCode("ABCD12");
        signUpRequest.setEmail("test1234@naver.com");
        signUpRequest.setNickname("테스트1");

        //when
        SignUpResponseDto signUpResponse = userService.registerUser(signUpRequest);

        //then
        assertThat(signUpRequest.getLoginId()).isEqualTo(signUpResponse.getLoginId());
        assertThat(signUpRequest.getEmail()).isEqualTo(signUpResponse.getEmail());
        assertThat(signUpRequest.getNickname()).isEqualTo(signUpResponse.getNickname());

    }


    /**
     * 사용하고자 하는 ID로 이미 가입된 회원이 있다면 예외 반환
     */
    @Test
    public void registerUser_회원가입_실패_아이디중복() {
        //given
        emailAuthRepository.save(EmailAuthEntity.builder()
                .email("test1234@naver.com")
                .authCode("ABCD12")
                .build());

        SignUpRequestDto signUpRequest = new SignUpRequestDto();
        signUpRequest.setLoginId("test1234");
        signUpRequest.setPassword("qwer1234!");
        signUpRequest.setAuthCode("ABCD12");
        signUpRequest.setEmail("test1234@naver.com");
        signUpRequest.setNickname("테스트1");
        userService.registerUser(signUpRequest);

        SignUpRequestDto signUpRequestExistsId = new SignUpRequestDto();
        signUpRequestExistsId.setLoginId("test1234"); //아이디 중복
        signUpRequestExistsId.setPassword("abcd1234!");
        signUpRequestExistsId.setAuthCode("QWER12");
        signUpRequestExistsId.setEmail("test@naver.com");
        signUpRequestExistsId.setNickname("테스트2");

        //when
        assertThatThrownBy(() ->{
            userService.registerUser(signUpRequestExistsId);
        }).isInstanceOf(CommonException.class);

    }

    /**
     * 가입하고자 하는 이메일로 이미 가입된 회원이 있다면 예외 반환
     */
    @Test
    public void registerUser_회원가입_실패_동일한_이메일로_재가입() {
        //given
        emailAuthRepository.save(EmailAuthEntity.builder()
                .email("test1234@naver.com")
                .authCode("ABCD12")
                .build());

        SignUpRequestDto signUpRequest = new SignUpRequestDto();
        signUpRequest.setLoginId("test1234");
        signUpRequest.setPassword("qwer1234!");
        signUpRequest.setAuthCode("ABCD12");
        signUpRequest.setEmail("test1234@naver.com");
        signUpRequest.setNickname("테스트1");
        userService.registerUser(signUpRequest);

        SignUpRequestDto signUpRequestExistsEmail = new SignUpRequestDto();
        signUpRequestExistsEmail.setLoginId("abcd1234");
        signUpRequestExistsEmail.setPassword("abcd1234!");
        signUpRequestExistsEmail.setAuthCode("QWER12");
        signUpRequestExistsEmail.setEmail("test1234@naver.com"); //이메일 재사용
        signUpRequestExistsEmail.setNickname("테스트2");

        //when
        assertThatThrownBy(() ->{
            userService.registerUser(signUpRequestExistsEmail);
        }).isInstanceOf(CommonException.class);

    }

    /**
     * 사용하고자 하는 닉네임으로 가입된 회원이 존재한다면 예외 반환
     */
    @Test
    public void registerUser_회원가입_실패_닉네임_중복() {
        //given
        emailAuthRepository.save(EmailAuthEntity.builder()
                .email("test1234@naver.com")
                .authCode("ABCD12")
                .build());

        SignUpRequestDto signUpRequest = new SignUpRequestDto();
        signUpRequest.setLoginId("test1234");
        signUpRequest.setPassword("qwer1234!");
        signUpRequest.setAuthCode("ABCD12");
        signUpRequest.setEmail("test1234@naver.com");
        signUpRequest.setNickname("테스트1");
        userService.registerUser(signUpRequest);

        SignUpRequestDto signUpRequestExistsNickname = new SignUpRequestDto();
        signUpRequestExistsNickname.setLoginId("abcd1234");
        signUpRequestExistsNickname.setPassword("abcd1234!");
        signUpRequestExistsNickname.setAuthCode("QWER12");
        signUpRequestExistsNickname.setEmail("test@naver.com");
        signUpRequestExistsNickname.setNickname("테스트1"); //닉네임 중복

        //when
        assertThatThrownBy(() ->{
            userService.registerUser(signUpRequestExistsNickname);
        }).isInstanceOf(CommonException.class);

    }

    /**
     * 이메일로 인증코드가 발송되지 않았다면 예외 반환
     */
    @Test
    public void registerUser_회원가입_실패_인증코드_미발송() {

        //given
        SignUpRequestDto signUpRequest = new SignUpRequestDto();
        signUpRequest.setLoginId("test1234");
        signUpRequest.setPassword("qwer1234!");
        signUpRequest.setAuthCode("ABCD12");
        signUpRequest.setEmail("test1234@naver.com");
        signUpRequest.setNickname("테스트1");

        //when
        assertThatThrownBy(() ->{
            userService.registerUser(signUpRequest);
        }).isInstanceOf(CommonException.class);

    }

    /**
     * 인증코드 유효시간이 지났을 경우 예외 반환 (import.sql 테스트 데이터 참고)
     */
    @Test
    public void registerUser_회원가입_실패_인증코드_시간초과() {
        //given
        SignUpRequestDto signUpRequest = new SignUpRequestDto();
        signUpRequest.setLoginId("test1234");
        signUpRequest.setPassword("qwer1234!");
        signUpRequest.setAuthCode("ABCD12");
        signUpRequest.setEmail("test1234@naver.com");
        signUpRequest.setNickname("테스트1");

        //when
        assertThatThrownBy(() ->{
            userService.registerUser(signUpRequest);
        }).isInstanceOf(CommonException.class);

    }

    /**
     * 인증코드 불일치일 경우 예외 반환
     */
    @Test
    public void registerUser_회원가입_실패_인증코드_불일치() {
        //given
        emailAuthRepository.save(EmailAuthEntity.builder()
                .email("test1234@naver.com")
                .authCode("ABCD12") //인증코드
                .build());
        
        SignUpRequestDto signUpRequest = new SignUpRequestDto();
        signUpRequest.setLoginId("test1234");
        signUpRequest.setPassword("qwer1234!");
        signUpRequest.setAuthCode("AAAAAA"); //인증코드 불일치
        signUpRequest.setEmail("test1234@naver.com");
        signUpRequest.setNickname("테스트1");

        //when
        assertThatThrownBy(() ->{
            userService.registerUser(signUpRequest);
        }).isInstanceOf(CommonException.class);

    }


    @Test
    public void checkLoginIdDuplicate_아이디_중복_검사_테스트() {

        //given
        emailAuthRepository.save(EmailAuthEntity.builder()
                .email("test@naver.com")
                .authCode("QWERTY")
                .build());

        SignUpRequestDto signUpRequest = new SignUpRequestDto();
        signUpRequest.setLoginId("test1234");
        signUpRequest.setPassword("qwer1234!");
        signUpRequest.setAuthCode("QWERTY");
        signUpRequest.setEmail("test@naver.com");
        signUpRequest.setNickname("테스트1");
        userRepository.save(SignUpRequestDto.mapToEntity(signUpRequest));

        //when
        boolean ifFalse = userService.checkLoginIdDuplicate(signUpRequest.getLoginId());
        boolean ifTrue = userService.checkLoginIdDuplicate("aaaa1234");

        //then
        assertThat(ifFalse).isFalse();
        assertThat(ifTrue).isTrue();
    }
}
