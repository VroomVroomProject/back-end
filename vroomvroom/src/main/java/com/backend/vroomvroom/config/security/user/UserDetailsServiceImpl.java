package com.backend.vroomvroom.config.security.user;

import com.backend.vroomvroom.common.exception.CommonException;
import com.backend.vroomvroom.common.exception.ErrorCode;
import com.backend.vroomvroom.entity.user.UserEntity;
import com.backend.vroomvroom.repository.user.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        UserEntity findUser = userRepository.findById(Long.valueOf(userId))
                .orElseThrow(() -> {
                    log.error("존재하지 않는 사용자입니다. userId : {}", userId);
                    throw new CommonException(ErrorCode.NOT_FOUND_ENTITY, "존재하지 않는 사용자입니다.");
                });

        return UserDetailsImpl.builder()
                .loginId(findUser.getLoginId())
                .password(findUser.getPassword())
                .authorities(findUser.getRoles().stream()
                        .map(auth -> new SimpleGrantedAuthority(auth.toString()))
                        .collect(Collectors.toList())
                ).build();
    }
}
