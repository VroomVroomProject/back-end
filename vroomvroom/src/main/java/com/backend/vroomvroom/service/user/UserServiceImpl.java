package com.backend.vroomvroom.service.user;

import com.backend.vroomvroom.common.exception.CommonException;
import com.backend.vroomvroom.config.security.jwt.JwtTokenProvider;
import com.backend.vroomvroom.dto.user.request.LoginRequestDto;
import com.backend.vroomvroom.dto.user.request.SignUpRequestDto;
import com.backend.vroomvroom.dto.user.request.TokenRequestDto;
import com.backend.vroomvroom.dto.user.response.LoginResponseDto;
import com.backend.vroomvroom.dto.user.response.SignUpResponseDto;
import com.backend.vroomvroom.dto.user.response.TokenResponseDto;
import com.backend.vroomvroom.entity.user.EmailAuthEntity;
import com.backend.vroomvroom.entity.user.UserEntity;
import com.backend.vroomvroom.repository.user.IEmailAuthRepository;
import com.backend.vroomvroom.repository.user.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.backend.vroomvroom.common.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final IEmailAuthRepository emailAuthRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public SignUpResponseDto registerUser(SignUpRequestDto signUpRequestDto) {

        if(userRepository.findByLoginId(signUpRequestDto.getLoginId()).isPresent()) {
            log.error("이미 존재하는 아이디 입니다. id : {}", signUpRequestDto.getLoginId());
            throw new CommonException(DUPLICATED_ENTITY, "이미 존재하는 아이디 입니다. id : " + signUpRequestDto.getLoginId());
        }

        if(userRepository.findByEmail(signUpRequestDto.getEmail()).isPresent()) {
            log.error("해당 이메일로 가입된 회원이 존재합니다. email : {}", signUpRequestDto.getEmail());
            throw new CommonException(DUPLICATED_ENTITY, "해당 이메일로 가입된 회원이 존재합니다. email : " + signUpRequestDto.getEmail());
        }
        
        if(userRepository.findByNickname(signUpRequestDto.getNickname()).isPresent()) {
            log.error("해당 닉네임으로 가입된 회원이 존재합니다. nickname : {}", signUpRequestDto.getNickname());
            throw new CommonException(DUPLICATED_ENTITY, "해당 닉네임으로 가입된 회원이 존재합니다. nickname : " + signUpRequestDto.getNickname());
        }

        this.validateEmail(signUpRequestDto.getEmail(), signUpRequestDto.getAuthCode());

        //비밀번호 암호화
        signUpRequestDto.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));

        UserEntity user = SignUpRequestDto.mapToEntity(signUpRequestDto);
        UserEntity savedUser = userRepository.save(user);

        return SignUpResponseDto.mapToDto(savedUser);
    }


    private void validateEmail(String email, String authCode) {
        EmailAuthEntity emailAuth = emailAuthRepository.findTop1ByEmailOrderBySendDateDesc(email)
                .orElseThrow(() -> {
                    log.error("해당 이메일로 인증코드가 발송되지 않았습니다. email : {}", email);
                    throw new CommonException(NOT_FOUND_ENTITY, "해당 이메일로 인증코드가 발송되지 않았습니다. email : " + email);
                });

        if(emailAuth.expireCheck()) {
            log.error("인증코드 유효시간이 지났습니다.");
            throw new CommonException(INVALID_REQUEST, "인증코드 유효시간(5분)이 지났습니다.");
        }

        if(!emailAuth.getAuthCode().equals(authCode)) {
            log.error("발송된 인증코드와 입력한 인증코드가 다릅니다. authCode : {}", authCode);
            throw new CommonException(INVALID_REQUEST, "발송된 인증코드와 입력한 인증코드가 다릅니다. authCode : " + authCode);
        }
    }

    @Override
    public boolean checkLoginIdDuplicate(String loginId) {
        return !userRepository.findByLoginId(loginId).isPresent();
    }

    @Override
    @Transactional
    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
        UserEntity findUser = this.findUser(loginRequestDto.getLoginId());

        if(!passwordEncoder.matches(loginRequestDto.getPassword(), findUser.getPassword())) {
            log.error("입력한 비밀번호가 일치하지 않습니다.");
            throw new CommonException(INVALID_REQUEST, "입력한 비밀번호가 올바른지 확인해 주세요.");
        }

        findUser.updateRefreshToken(jwtTokenProvider.createRefreshToken());

        return LoginResponseDto.mapToDto(jwtTokenProvider.createAccessToken(findUser.getId()), findUser.getRefreshToken());
    }

    private UserEntity findUser(String loginId) {
        return userRepository.findByLoginId(loginId)
                .orElseThrow(() -> {
                    log.error("입력한 아이디가 존재하지 않습니다. loginId : {}", loginId);
                    throw new CommonException(NOT_FOUND_ENTITY, "입력한 아이디가 올바른지 확인해 주세요.");
                });
    }

    @Override
    @Transactional
    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {
        if(!jwtTokenProvider.validateTokenExpiration(tokenRequestDto.getRefreshToken())) {
            throw new CommonException(INVALID_REQUEST, "Refresh Token이 유효하지 않습니다.");
        }

        UserEntity findUser = findUserByToken(tokenRequestDto);

        if(!findUser.getRefreshToken().equals(tokenRequestDto.getRefreshToken())) {
            throw new CommonException(INVALID_REQUEST, "Refresh Token이 일치하지 않습니다. 다시 로그인해 주세요.");
        }

        String accessToken = jwtTokenProvider.createAccessToken(findUser.getId());
        String refreshToken = jwtTokenProvider.createRefreshToken();

        findUser.updateRefreshToken(refreshToken);

        return TokenResponseDto.mapToDto(accessToken, refreshToken);
    }

    private UserEntity findUserByToken(TokenRequestDto tokenRequestDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtTokenProvider.getUserIdByExpiredToken(tokenRequestDto.getAccessToken()));
        String loginId = userDetails.getUsername();
        return this.findUser(loginId);
    }


}
