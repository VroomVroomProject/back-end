package com.backend.vroomvroom.service.user;

import com.backend.vroomvroom.common.exception.CommonException;
import com.backend.vroomvroom.common.utils.RandomCode;
import com.backend.vroomvroom.entity.user.EmailAuthEntity;
import com.backend.vroomvroom.repository.user.IEmailAuthRepository;
import com.backend.vroomvroom.repository.user.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.backend.vroomvroom.common.exception.ErrorCode.*;

@Slf4j
@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    private final IUserRepository userRepository;
    private final IEmailAuthRepository emailAuthRepository;
    private final JavaMailSender javaMailSender;

    @Override
    @Transactional
    public void authenticationEmail(String email) {

        if(userRepository.findByEmail(email).isPresent()) {
            log.error("해당 이메일로 가입된 회원이 존재합니다. email : {}", email);
            throw new CommonException(DUPLICATED_ENTITY, "해당 이메일로 가입된 회원이 존재합니다. email : " + email);
        }

        String authCode = RandomCode.getCoode(6);

        emailAuthRepository.save(EmailAuthEntity.builder()
                .email(email)
                .authCode(authCode)
                .build());

        send(email, authCode);
    }

    @Async
    private void send(String email, String authCode) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("[vroom] 회원가입 이메일 인증");
        mailMessage.setText("회원가입 인증 코드는 " + authCode + " 입니다.");

        log.info("{} 로 인증코드를 방송 했습니다. code : {}", email, authCode);

        javaMailSender.send(mailMessage);
    }


}
